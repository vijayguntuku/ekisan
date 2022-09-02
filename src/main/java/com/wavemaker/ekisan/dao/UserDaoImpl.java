package com.wavemaker.ekisan.dao;

import com.mysql.cj.protocol.Resultset;
import com.wavemaker.ekisan.dto.Orders;
import com.wavemaker.ekisan.dto.User;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

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
            user.setId(resultset.getInt("id"));
            user.setEmail(resultset.getString("email"));
            user.setName(resultset.getString("Name"));
            user.setRole(resultset.getInt("role"));

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

    @Override
        public boolean saveOrUpdateUser(User user) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            String query=null;
            if(user.getId() != 0){
                query ="update user set email=?,password=?,name=?,role=? where id=?";
            }else {
                query="insert into user(email,password,name,role) values(?,?,?,?)";
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getName());
            preparedStatement.setInt(4,user.getRole());
            if(user.getId()!= 0) {
                preparedStatement.setInt(5, user.getId());
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
            throw new DatabaseException("Exception occured while updating data into Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while inserting data into Database.", exception);
            throw new DatabaseException("Exception occurred while inserting data into Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return false;
    }

    @Override
    public User findUserByID(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        User user = null;
        try {
            query = "select * from users where id=? ";
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultset = preparedStatement.executeQuery();
            user = new User();
            while (resultset.next()) {
                user.setId(resultset.getInt("id"));
                user.setEmail(resultset.getString("email"));
                user.setName(resultset.getString("name"));
                user.setRole(resultset.getInt("role"));

            }

        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while displaying data from Database Connection.", databaseException);
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
        return user;
    }
}
