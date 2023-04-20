package com.gmail.spanteleyko.service;

import com.gmail.spanteleyko.service.model.ItemDTO;
import com.gmail.spanteleyko.service.model.ShopDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface ShopService {

    ShopDTO create(String name, String location) throws SQLException;

    void add(ShopDTO shop, List<ItemDTO> items) throws SQLException;

    void clear() throws SQLException;
}
