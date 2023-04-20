package com.gmail.spanteleyko.repository.exception;

import java.sql.SQLException;

public class AddPriceException extends SQLException {
    public AddPriceException(String error) {
        super(error);
    }
}
