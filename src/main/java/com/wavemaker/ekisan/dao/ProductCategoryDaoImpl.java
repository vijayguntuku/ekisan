package com.wavemaker.ekisan.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.ekisan.dto.ProductCategory;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;

public class ProductCategoryDaoImpl implements ProductCategoryDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductCategoryDaoImpl.class);


    @Override
    public List<ProductCategory> findAllProductCategories() {
        List<ProductCategory> productCatList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ProductCategory category = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement("select pc.* from product_category pc ");
            ResultSet resultset = preparedStatement.executeQuery();

            while (resultset.next()) {
                category = new ProductCategory();
                category.setId(resultset.getInt("id"));
                category.setName(resultset.getString("name"));
                productCatList.add(category);
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
        return productCatList;
    }

}
