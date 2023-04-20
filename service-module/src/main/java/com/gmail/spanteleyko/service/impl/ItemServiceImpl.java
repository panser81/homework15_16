package com.gmail.spanteleyko.service.impl;

import com.gmail.spanteleyko.repository.ItemRepository;
import com.gmail.spanteleyko.repository.model.Item;
import com.gmail.spanteleyko.service.ItemService;
import com.gmail.spanteleyko.service.converter.ItemConverter;
import com.gmail.spanteleyko.repository.exception.CreateShopException;
import com.gmail.spanteleyko.service.model.ItemDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ItemServiceImpl implements ItemService {
    private static final int ITEMS_COUNT = 10;
    private final ItemConverter itemConverter;
    private final ItemRepository itemRepository;
    @Autowired
    private DataSource dataSource;

    @Override
    public List<ItemDTO> generateItems() throws SQLException {
        List<ItemDTO> items = new ArrayList<>();

        for (int i = 1; i <= ITEMS_COUNT; i++) {
            ItemDTO item = generateItem(i);
            items.add(item);
        }

        return items;
    }

    private ItemDTO generateItem(int i) throws SQLException {
        Item item = new Item();
        item.setDescription(String.format("description for %d item", i));
        item.setName(String.format("name-%d", i));

        ItemDTO createdItem = addToDb(item);

        createdItem.setPrice(Double.valueOf(i));
        ItemDTO updatedItem = savePrice(createdItem);

        return updatedItem;
    }

    private ItemDTO savePrice(ItemDTO itemDTO) throws SQLException {

        Item item = itemConverter.convert(itemDTO);
        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(false);
            Item updatedItem;
            try {
                updatedItem = itemRepository.addPrice(connection, item);
            } catch (SQLException e) {
                connection.rollback();
                throw new CreateShopException(e.getMessage());
            }
            connection.commit();
            return itemConverter.convertToDTO(updatedItem);
        }
    }

    private ItemDTO addToDb(Item item) throws SQLException {

        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(false);
            Item createdItem;
            try {
                createdItem = itemRepository.add(connection, item);
            } catch (SQLException e) {
                connection.rollback();
                throw new CreateShopException(e.getMessage());
            }
            connection.commit();
            return itemConverter.convertToDTO(createdItem);
        }
    }
}
