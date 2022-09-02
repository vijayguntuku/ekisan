package com.wavemaker.ekisan.dao;

import com.wavemaker.ekisan.dto.Address;
import com.wavemaker.ekisan.exception.DatabaseException;
import com.wavemaker.ekisan.utility.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class AddressDaoImpl implements AddressDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(AddressDaoImpl.class);

    @Override
    public Address findAddressByID(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        String query = null;
        Address address=null;

        try {
            query = "select * from address where id=?";
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            ResultSet resultset = preparedStatement.executeQuery();
            address = new Address();
            while (resultset.next()) {
                address.setId(resultset.getInt("id"));
                address.setAddress1(resultset.getString("address1"));
                address.setAddress2(resultset.getString("address2"));
                address.setCity(resultset.getString("city"));
                address.setState(resultset.getString("state"));
                address.setCountry(resultset.getString("country"));
                address.setPincode(resultset.getFloat("pincode"));
                address.setUpdatedAt(resultset.getDate("updatedAt"));
                address.setUpdatedBy(resultset.getInt("updatedBy"));
                address.setAddress_type(resultset.getInt("address_type"));

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
        return address;
    }

    @Override
    public boolean saveOrUpdate(Address address)
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DBConnection.getConnectionNonSingleTon();
            String query=null;
            if(address.getId() != 0){
                query="update address set address1= ?,address2 =?,city = ?,state =?,country = ?,pincode =?,updatedAt = ?,updatedBy = ?,address_type =? where id= ?";
            }else {
                query="insert into address (address1,address2,city,state,country,pincode,updatedAt,updatedBy,address_type) values(?,?,?,?,?,?,?,?,?)";
            }
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,address.getAddress1());
            preparedStatement.setString(2,address.getAddress2());
            preparedStatement.setString(3,address.getCity());
            preparedStatement.setString(4,address.getState());
            preparedStatement.setString(5,address.getCountry());
            preparedStatement.setFloat(6,address.getPincode());
            preparedStatement.setDate(7, (Date) address.getUpdatedAt());
            preparedStatement.setInt(8,address.getUpdatedBy());
            preparedStatement.setInt(9,address.getAddress_type());
            if(address.getId()!= 0) {
                preparedStatement.setInt(10, address.getId());
            }

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }


        } catch (DatabaseException databaseException) {
            LOGGER.error("Exception while updating data into Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        } catch (SQLException exception) {
            LOGGER.error("SQLException occured while update data into Database.", exception);
            throw new DatabaseException("Exception occurred while updating data into Database.");
        } catch (Exception exception) {
            LOGGER.error("Exception occurred while updating data into Database.", exception);
            throw new DatabaseException("Exception occurred while updating data into Database.");
        } finally {
            DBConnection.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean deleteAddress(int id) {
        Connection connection= null;
        PreparedStatement preparedStatement = null;
        boolean isDeleted=false;
        try{
            connection = DBConnection.getConnectionNonSingleTon();
            preparedStatement= connection.prepareStatement("delete from address where id=? ");
            preparedStatement.setInt(1,id);

            int count = preparedStatement.executeUpdate();
            isDeleted = count >0 ? true : false ;
        }catch (DatabaseException databaseException){
            LOGGER.error("Exception while deleting Database Connection.", databaseException);
            // databaseException.printStackTrace();
            throw databaseException;
        }catch (SQLException exception){
            LOGGER.error("SQLException occured while deleting data from Database.", exception);
            throw new DatabaseException("Exception occured while deleting data from Database.");
        }catch (Exception exception){
            LOGGER.error("Exception occured while deleting data from Database.", exception);
            throw new DatabaseException("Exception occured while deleting data from Database.");
        }finally {
            DBConnection.closeConnection(connection);
        }
        return isDeleted;
    }
    }

