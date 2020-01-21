package mate.academy.internetshop.dao.jbbc;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.Item;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = Logger.getLogger(ItemDaoJdbcImpl.class);
    private static String DB_NAME = "internetshop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item item) {
        String query = String.format("INSERT INTO %s.items (name, price)"
                        + " VALUES (%s, %.2f);", DB_NAME, item.getName(), item.getPrice());
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e ) {
            logger.warn("Can't create item", e);
        }
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format("SELECT * FROM %s.items WHERE item_id = %d ;", DB_NAME, id);
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                long item_id = rs.getLong("item_id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Item item = new Item();
                item.setName(name);
                item.setPrice(price);
                item.setId(id);
                return Optional.of(item);
            }
        } catch (SQLException e ) {
            logger.warn("Can't get item by id=" + id);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item item) {
        String query = String.format( "UPDATE %s.items SET name = %s, price = %.2f WHERE (item_id = %d);"
                , DB_NAME, item.getName(), item.getPrice(), item.getId() );
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e ) {
            logger.warn("Can't update item", e);
        }
        return item;
    }

    @Override
    public boolean delete(Long id) {
        String query = String.format("DELETE FROM %s.items where item_id = %d ;", DB_NAME, id);
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't delete item", e);
        }
        return true;
    }

    @Override
    public List<Item> getAll() {
        return null;
    }
}
