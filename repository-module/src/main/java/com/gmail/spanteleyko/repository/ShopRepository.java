package com.gmail.spanteleyko.repository;

import com.gmail.spanteleyko.repository.exception.AddItemToShopException;
import com.gmail.spanteleyko.repository.exception.CleanItemsException;
import com.gmail.spanteleyko.repository.model.Item;
import com.gmail.spanteleyko.repository.model.Shop;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ShopRepository {
    Shop add(Connection connection, Shop shop) throws SQLException;

    void addItems(Connection connection, Shop shop, List<Item> items) throws AddItemToShopException;

    void clear(Connection connection) throws CleanItemsException;
}
