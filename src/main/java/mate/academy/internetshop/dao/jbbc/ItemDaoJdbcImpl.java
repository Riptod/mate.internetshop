package mate.academy.internetshop.dao.jbbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.Item;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) throws DataProcessingException {
        String query = "INSERT INTO items (name, price) VALUES (?, ?);";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(1, item.getPrice());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    item.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating item failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create item: ", e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM items WHERE item_id = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setId(id);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get item: ", e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        String query = "UPDATE items SET name = ?, price = ? WHERE item_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setLong(3, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update item: ", e);
        }
        return item;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM items where item_id = ? ;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete item: ", e);
        }
        return true;
    }

    @Override
    public List<Item> getAll() throws DataProcessingException {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to getAll items: ", e);
        }
        return items;
    }
}
