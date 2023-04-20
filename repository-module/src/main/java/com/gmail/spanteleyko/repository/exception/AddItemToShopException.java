package com.gmail.spanteleyko.repository.exception;

import java.sql.SQLException;

public class AddItemToShopException extends SQLException {
    public AddItemToShopException(String error) {
        super(error);
    }
}