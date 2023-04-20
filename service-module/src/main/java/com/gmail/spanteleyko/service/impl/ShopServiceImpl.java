package com.gmail.spanteleyko.service.impl;

import com.gmail.spanteleyko.repository.ShopRepository;
import com.gmail.spanteleyko.repository.exception.AddItemToShopException;
import com.gmail.spanteleyko.repository.exception.CleanItemsException;
import com.gmail.spanteleyko.repository.model.Item;
import com.gmail.spanteleyko.repository.model.Shop;
import com.gmail.spanteleyko.service.ShopService;
import com.gmail.spanteleyko.service.converter.ItemConverter;
import com.gmail.spanteleyko.service.converter.ShopConverter;
import com.gmail.spanteleyko.repository.exception.CreateShopException;
import com.gmail.spanteleyko.service.model.ItemDTO;
import com.gmail.spanteleyko.service.model.ShopDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ShopServiceImpl implements ShopService {

    private final ShopRepository shopRepository;
    private final ShopConverter shopConverter;
    private final ItemConverter itemConverter;

    @Autowired
    private DataSource dataSource;

    @Override
    public ShopDTO create(String name, String location) throws SQLException {

        ShopDTO shopDTO = new ShopDTO();
        shopDTO.setName(name);
        shopDTO.setLocation(location);

        return addToDb(shopDTO);
    }

    @Override
    public void add(ShopDTO shopDTO, List<ItemDTO> itemDTOS) throws SQLException {

        Shop shop = shopConverter.convert(shopDTO);

        List<Item> items = itemDTOS.stream()
                .map((ItemDTO item) -> itemConverter.convert(item))
                .collect(Collectors.toList());

        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(false);

            try {
                shopRepository.addItems(connection, shop, items);
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                throw new AddItemToShopException(e.getMessage());
            }
        }
    }

    @Override
    public void clear() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);
            try {
                shopRepository.clear(connection);
            } catch (SQLException e) {
                connection.rollback();
                throw new CleanItemsException(e.getMessage());
            }
            connection.commit();
        }
    }

    private ShopDTO addToDb(ShopDTO shopDTO) throws SQLException {
        Shop shop = shopConverter.convert(shopDTO);

        try (Connection connection = dataSource.getConnection()) {

            connection.setAutoCommit(false);
            Shop createdShop;
            try {
                createdShop = shopRepository.add(connection, shop);
            } catch (SQLException e) {
                connection.rollback();
                throw new CreateShopException(e.getMessage());
            }
            connection.commit();
            return shopConverter.convertToDTO(createdShop);
        }
    }
}
