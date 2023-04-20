package com.gmail.spanteleyko.repository.exception;

import java.sql.SQLException;

public class CleanItemsException extends SQLException {
    public CleanItemsException(String error) {
        super(error);
    }
}
