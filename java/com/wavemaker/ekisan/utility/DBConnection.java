package com.wavemaker.ekisan.utility;

import com.wavemaker.ekisan.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static String MYSQL_URL = "jdbc:mysql://localhost:3306/ekisan";
    private static String MYSQL_USER_NAME = "root";
    private static String MYSQL_PASSWORD = "Vijay123$";

    private static Connection connection;

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new DatabaseException("Failed to register mysql driver", e);
        }
    }
    public static Connection getConnectionInSingleTon() throws DatabaseException {
        if(connection == null) {
            connection = createConnection(MYSQL_URL, MYSQL_USER_NAME, MYSQL_PASSWORD);
        }
        return connection;
    }

    public static Connection getConnection() throws DatabaseException {
        if (connection == null) {
            synchronized (DBConnection.class) {
                if(connection == null) {
                    connection = createConnection(MYSQL_URL, MYSQL_USER_NAME, MYSQL_PASSWORD);
                }
            }
        }
        return connection;
    }

    public static Connection getConnectionNonSingleTon() throws DatabaseException {
        Connection mySQLConnection = createConnection(MYSQL_URL, MYSQL_USER_NAME, MYSQL_PASSWORD);
        return mySQLConnection;
    }

    private static Connection createConnection(String mysqlUrl, String mysqlUserName, String mysqlPassword) throws DatabaseException{
        try {
            LOGGER.info("Creating database connection to {} and username {}", mysqlUrl, mysqlUserName);
            return DriverManager.getConnection(mysqlUrl, mysqlUserName, mysqlPassword);
        } catch (SQLException e) {
            throw new DatabaseException("Failed to create connection", e);
        }
    }

    public static void closeConnection(Connection connection){
        try{
            if(connection!=null)
                connection.close();
        } catch (SQLException e) {
            LOGGER.warn("SQL Exception while closing Mysql Connection {} With User {}", MYSQL_URL, MYSQL_USER_NAME, e);
        }
    }

}
