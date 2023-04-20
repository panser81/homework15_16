package com.gmail.spanteleyko.service;

import com.gmail.spanteleyko.service.model.ItemDTO;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    List<ItemDTO> generateItems() throws SQLException;
}
