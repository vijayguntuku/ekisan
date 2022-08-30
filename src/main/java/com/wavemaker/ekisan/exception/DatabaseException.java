package com.wavemaker.ekisan.exception;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message){
        super(message);
    }

    public DatabaseException(String message, Throwable th){
        super(message, th);
    }

}
