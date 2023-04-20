package com.gmail.spanteleyko.repository.exception;

import java.sql.SQLException;

public class CreateShopException extends SQLException {
    public CreateShopException(String error) {
        super(error);
    }
}
