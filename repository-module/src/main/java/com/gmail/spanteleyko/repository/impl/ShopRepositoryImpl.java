package com.gmail.spanteleyko.repository.impl;

import com.gmail.spanteleyko.repository.ShopRepository;
import com.gmail.spanteleyko.repository.exception.AddItemToShopException;
import com.gmail.spanteleyko.repository.exception.CleanItemsException;
import com.gmail.spanteleyko.repository.exception.CreateShopException;
import com.gmail.spanteleyko.repository.model.Item;
import com.gmail.spanteleyko.repository.model.Shop;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class ShopRepositoryImpl implements ShopRepository {

    @Override
    public Shop add(Connection connection, Shop shop) throws CreateShopException {
        String sql = """
                insert into shop (name, location)
                values(?, ?)
                RETURNING id;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setString(2, shop.getLocation());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating shop failed, no rows affected");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    shop.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating shop failed, no Id obtained");
                }
            }
        } catch (SQLException e) {
            throw new CreateShopException(e.getMessage());
        }
        return shop;
    }

    @Override
    public void addItems(Connection connection, Shop shop, List<Item> items) throws AddItemToShopException {
        String sql = """
                insert into item_shop (item_id, shop_id)
                values(?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (Item item : items) {
                preparedStatement.setLong(1, item.getId());
                preparedStatement.setLong(2, shop.getId());

                int affectedRows = preparedStatement.executeUpdate();

                if (affectedRows == 0) {
                    throw new AddItemToShopException("Adding item to shop failed, no rows affected");
                }
            }
        } catch (SQLException e) {
            throw new AddItemToShopException(e.getMessage());
        }
    }

    @Override
    public void clear(Connection connection) throws CleanItemsException {
        String sql = """
                delete from itemdetails where item_id in (
                select d.item_id from itemdetails d
                    left join item_shop s on d.item_id = s.item_id
                    where s.shop_id is null);
                delete from item where id in (
                    select i.id from item i
                        left join item_shop s on i.id = s.item_id
                    where s.shop_id is null);""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new AddItemToShopException("Clean items failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new CleanItemsException(e.getMessage());
        }
    }
}
