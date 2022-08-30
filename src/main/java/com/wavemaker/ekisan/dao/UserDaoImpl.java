package com.wavemaker.ekisan.dao;

import com.mysql.cj.protocol.Resultset;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    @Override
    public User findUser(String username, String password) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        User user = null;
        try{
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement= connection.prepareStatement("select * from user where username=? and password=?");
            preparedStatement.setString(1,username);
            preparedStatement.setString(2, password);
            ResultSet resultset = preparedStatement.executeQuery();

            resultset.next();
            user = new User();
            user.setUsername(resultset.getString("username"));
            //user.setFirstName(resultset.getString("firstname"));
            user.setRole(resultset.getString("role"));

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
        return user;
    }
}
