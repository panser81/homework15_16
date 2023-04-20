package com.gmail.spanteleyko.repository;

import com.gmail.spanteleyko.repository.exception.AddPriceException;
import com.gmail.spanteleyko.repository.exception.CreateItemException;
import com.gmail.spanteleyko.repository.model.Item;

import java.sql.Connection;

public interface ItemRepository {
    Item add(Connection connection, Item item) throws CreateItemException;

    Item addPrice(Connection connection, Item item) throws AddPriceException;
}
