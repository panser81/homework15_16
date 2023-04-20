package com.gmail.spanteleyko.repository.impl;

import com.gmail.spanteleyko.repository.ItemRepository;
import com.gmail.spanteleyko.repository.exception.AddPriceException;
import com.gmail.spanteleyko.repository.exception.CreateItemException;
import com.gmail.spanteleyko.repository.model.Item;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class ItemRepositoryImpl implements ItemRepository {
    @Override
    public Item add(Connection connection, Item item) throws CreateItemException {
        String sql = """
                insert into item (name, description)
                values(?, ?)
                RETURNING id;
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating item failed, no rows affected");
            }
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating item failed, no Id obtained");
                }
            }
        } catch (SQLException e) {
            throw new CreateItemException(e.getMessage());
        }
        return item;
    }

    @Override
    public Item addPrice(Connection connection, Item item) throws AddPriceException {
        String sql = """
                insert into itemdetails (item_id, price)
                values(?, ?);
                """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, item.getId());
            preparedStatement.setDouble(2, item.getPrice());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating price failed, no rows affected");
            }
        } catch (SQLException e) {
            throw new AddPriceException(e.getMessage());
        }
        return item;
    }
}
