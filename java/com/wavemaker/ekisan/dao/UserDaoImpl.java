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
    public User findUser(String email, String password) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        User user = null;
        try{
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement= connection.prepareStatement("select u.*,r.name as rolename  from user u inner join role r on u.role=r.id where email=? and password=?");
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, password);
            ResultSet resultset = preparedStatement.executeQuery();

            resultset.next();
            user = new User();
            user.setEmail(resultset.getString("email"));
            user.setName(resultset.getString("Name"));
            user.setRole(resultset.getString("rolename"));

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
