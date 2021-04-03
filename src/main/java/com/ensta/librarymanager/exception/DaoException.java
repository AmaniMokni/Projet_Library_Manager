package com.ensta.librarymanager.exception;

public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String msg) {
        super(msg);
    }
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }


}
