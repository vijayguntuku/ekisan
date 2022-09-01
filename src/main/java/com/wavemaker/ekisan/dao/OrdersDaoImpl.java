package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.Product;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                orders.setDelivery_address(resultset.getInt("delivery_address"));
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
        return null;
    }

}
