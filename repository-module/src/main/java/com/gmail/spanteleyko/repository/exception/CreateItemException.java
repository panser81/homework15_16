package com.gmail.spanteleyko.repository.exception;

import java.sql.SQLException;

public class CreateItemException extends SQLException {
    public CreateItemException(String error) {
        super(error);
    }
}