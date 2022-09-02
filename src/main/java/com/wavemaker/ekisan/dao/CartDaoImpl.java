package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Cart;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class CartDaoImpl implements CartDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDaoImpl.class);

    @Override
    public Cart findCartByID(int id) {
       return findCartByUserIDAndProductId(id, 0);
    }

    @Override
    public boolean saveOrUpdateCart(Cart cart) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            String query=null;
            Cart c = findCartByUserIDAndProductId(cart.getUserId(), cart.getProductId());
            if(c!=null){
                query="update cart set quantity = quantity+1 where productId= ? and userId=?";
            }else {
                query="insert into cart (productId,userId,quantity) values(?,?,?)";
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,cart.getProductId());

            preparedStatement.setInt(2,cart.getUserId());
            if(c==null)
                preparedStatement.setInt(3, cart.getQuantity());


            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }


        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while updating data into cart Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        } catch (SQLException exception) {
            LOGGER.error("SQLException occurred while update data into cart Database.", exception);
            throw new DatabaseException("Exception occurred while updating data into cart Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while updating data into cart Database.", exception);
            throw new DatabaseException("Exception occurred while updating data into cart Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean deleteCart(int id) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted=false;
        try{
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement= connection.prepareStatement("delete from cart where userId=? ");
            preparedStatement.setInt(1,id);

            int count = preparedStatement.executeUpdate();
            isDeleted = count >0 ? true : false ;
        }catch (DatabaseException databaseException){
            LOGGER.error("Exception while deleting data into cart Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        }catch (SQLException exception){
            LOGGER.error("SQLException occurred while deleting data from cart Database.", exception);
            throw new DatabaseException("Exception occurred while deleting data from cart Database.");
        }catch (Exception exception){
            LOGGER.error("Exception occurred while deleting data from cart Database.", exception);
            throw new DatabaseException("Exception occured while deleting data from Database.");
        }finally {
            DBConnection.closeConnection(connection);
        }
        return isDeleted;
    }

    @Override
    public Cart findCartByUserIDAndProductId(int userId, int productId) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        Cart cart =null;

        try {
            query = "select * from cart where userId=?";
            if(productId>0)
                query = query + " and productId=?";
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, userId);
            if(productId>0)
             preparedStatement.setInt(2, productId);

            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                cart = new Cart();
                cart.setUserId(resultset.getInt("userId"));
                cart.setProductId(resultset.getInt("productId"));
                cart.setQuantity(resultset.getInt("quantity"));

            }

        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while displaying data from cart", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        } catch (SQLException exception) {
            LOGGER.error("SQLException occured while reading data from cart Database.", exception);
            throw new DatabaseException("Exception occured while reading data fromcart  Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return cart;
    }
}
