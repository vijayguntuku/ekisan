package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl implements ProductDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDaoImpl.class);


    @Override
    public Product findProductByID(int id, String status) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        String query = null;
        Product product = null;
        try{
            query = "select * from product where id=? ";
            if(null!=status)
                query = query+"and status=?";
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement= connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            if(null!=status)
                preparedStatement.setString(2, status);
            ResultSet resultset = preparedStatement.executeQuery();
            product = new Product();
            while(resultset.next()){
                product.setId(resultset.getInt("id"));
                product.setName(resultset.getString("name"));
                product.setDescription(resultset.getString("description"));
                product.setPrice(resultset.getFloat("price"));
                product.setImage(resultset.getString("image"));
                product.setProductCategoryId(resultset.getInt("productCategoryId"));
                product.setUpdatedBy(resultset.getInt("updatedBy"));
                product.setStatus(resultset.getString("status"));
            }

        }catch (DatabaseException databaseException){
            LOGGER.error("Exception while creating Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        }catch (SQLException exception){
            LOGGER.error("SQLException occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        }catch (Exception exception){
            LOGGER.error("Exception occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        }finally {
            DBConnection.closeConnection(connection);
        }
        return product;
    }

    @Override
    public List<Product> findAllProducts(int categoryId) {
        List<Product> productList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        Product product = null;
        String query=null;
        if(categoryId != 0){
            query="select p.*,pc.id as productCategoryId, pc.name as productCategoryName, pc.description as productCategoryDesc from product p inner join product_category pc on p.product_category_id=pc.id where p.product_category_id="+categoryId;
        }else {
            query="select p.*,pc.id as productCategoryId, pc.name as productCategoryName, pc.description as productCategoryDesc from product p inner join product_category pc on p.product_category_id=pc.id";
        }
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement(query);
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                product = new Product();
                product.setId(resultset.getInt("id"));
                product.setName(resultset.getString("name"));
                product.setDescription(resultset.getString("description"));
                product.setPrice(resultset.getFloat("price"));
                product.setImage(resultset.getString("image"));
                product.setProductCategoryId(resultset.getInt("productCategoryId"));
                product.setUpdatedBy(resultset.getInt("updatedBy"));
                product.setStatus(resultset.getString("status"));
                product.setProductCategoryName(resultset.getString("productCategoryName"));
                productList.add(product);
            }

        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while creating Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        } catch (SQLException exception) {
            LOGGER.error("SQLException occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return productList;
    }

    public boolean saveOrUpdate(Product product){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        //Product product = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            String query=null;
            if(product.getId() != 0){
                query="update product set name= ?,description =?,price = ?,image =?,product_category_id = ?,updatedBy =?,status = ? where id= ?";
            }else {
                query="insert into product(name,description,price,image,product_category_id,updatedBy,status) values(?,?,?,?,?,?,?)";
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,product.getName());
            preparedStatement.setString(2,product.getDescription());
            preparedStatement.setFloat(3,product.getPrice());
            preparedStatement.setString(4,product.getImage());
            preparedStatement.setInt(5,product.getProductCategoryId());
            preparedStatement.setInt(6,product.getUpdatedBy());
            preparedStatement.setString(7,product.getStatus());
            if(product.getId()!= 0) {
                preparedStatement.setInt(8, product.getId());
            }

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }


        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while inserting data into Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        } catch (SQLException exception) {
            LOGGER.error("SQLException occured while inserting data into Database.", exception);
            throw new DatabaseException("Exception occured while inserting data into Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occured while inserting data into Database.", exception);
            throw new DatabaseException("Exception occured while inserting data into Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
    return false;
    }
    @Override
    public boolean deleteProduct(int id) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted=false;
        try{
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement= connection.prepareStatement("delete from product where id=? ");
            preparedStatement.setInt(1,id);

            int count = preparedStatement.executeUpdate();
            isDeleted = count >0 ? true : false ;
        }catch (DatabaseException databaseException){
            LOGGER.error("Exception while creating Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        }catch (SQLException exception){
            LOGGER.error("SQLException occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        }catch (Exception exception){
            LOGGER.error("Exception occured while reading data from Database.", exception);
            throw new DatabaseException("Exception occured while reading data from Database.");
        }finally {
            DBConnection.closeConnection(connection);
        }
        return isDeleted;
    }
}
