package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersDaoImpl implements OrdersDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrdersDaoImpl.class);
    @Override
    public Orders findOrderByID(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        Orders orders = null;
        try {
            query = "select * from orders where id=? ";
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultset = preparedStatement.executeQuery();
            orders = new Orders();
            while (resultset.next()) {
                orders.setId(resultset.getInt("id"));
                orders.setTotal_items(resultset.getInt("total_items"));
                orders.setTotal_amount(resultset.getFloat("total_amount"));
                orders.setDate(resultset.getDate("order_date"));
                orders.setDelivery_address_id(resultset.getInt("delivery_address"));
                orders.setUpdatedAt(resultset.getDate("updatedAt"));
                orders.setUpdatedBy(resultset.getInt("updatedBy"));

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
        return orders;
    }

    @Override
    public List<Orders> findAllOrders() {
        List<Orders> ordersList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Orders orders = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement("SELECT o.*, od.* FROM orders o LEFT JOIN order_details od ON od.order_id = o.id");
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                orders = new Orders();
                orders.setId(resultset.getInt("id"));
                orders.setTotal_items(resultset.getInt("total_items"));
                orders.setTotal_amount(resultset.getFloat("total_amount"));
                orders.setDate(resultset.getDate("order_date"));
                orders.setDelivery_address_id(resultset.getInt("delivery_address_id"));
                orders.setUpdatedAt(resultset.getDate("updatedAt"));
                orders.setUpdatedBy(resultset.getInt("updatedBy"));
                orders.setStatus(resultset.getString("status"));

                ordersList.add(orders);
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
        return ordersList;
    }

    public boolean saveOrUpdate(Orders orders){
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            String query=null;
            if(orders.getId() != 0){
                query="update orders set total_items= ?,total_amount =?,order_date = ?,delivery_address_id =?,updatedAt=?,updatedBy =?,status = ? where id= ?";
            }else {
                query="insert into orders(total_items,total_amount,order_date,delivery_address_id,updatedAt,updatedBy,status) values(?,?,?,?,?,?,?)";
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,orders.getTotal_items());
            preparedStatement.setFloat(2,orders.getTotal_amount());
            preparedStatement.setDate(3, (Date) orders.getDate());
            preparedStatement.setInt(4,orders.getDelivery_address_id());
            preparedStatement.setDate(5, (Date) orders.getUpdatedAt());
            preparedStatement.setInt(6,orders.getUpdatedBy());
            preparedStatement.setString(7,orders.getStatus());
            if(orders.getId()!= 0) {
                preparedStatement.setInt(8, orders.getId());
            }

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }


        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while saving data into Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        } catch (SQLException exception) {
            LOGGER.error("SQLException occured while saving data into Database.", exception);
            throw new DatabaseException("Exception occured while inserting data into Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occured while inserting data into Database.", exception);
            throw new DatabaseException("Exception occured while inserting data into Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return false;
    }
    }


