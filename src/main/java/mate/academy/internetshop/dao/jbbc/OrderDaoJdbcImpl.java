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
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.models.Order;

@Dao
public class OrderDaoJdbcImpl extends AbstractDao<Order> implements OrderDao {

    @Inject
    private static ItemDao itemDao;

    public OrderDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Order create(Order order) throws DataProcessingException {
        String query = "INSERT INTO orders (user_id) VALUES (?);";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, order.getUserId());
            stmt.executeUpdate();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
                while (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                }

        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create order: ", e);
        }

        for (Item item : order.getItems()) {
            addItem(order.getId(), item.getId());
        }
        return order;
    }

    @Override
    public Optional<Order> get(Long id) throws DataProcessingException {
        String query = "SELECT order_id, user_id, item_id FROM orders INNER JOIN orders_items"
                + " USING (order_id) WHERE order_id = ?;";
        Order order = new Order();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long orderId = rs.getLong("order_id");
                long userId = rs.getLong("user_id");
                long itemId = rs.getLong("item_id");
                order.setId(orderId);
                order.setUserId(userId);
                Optional<Item> item = itemDao.get(itemId);
                order.getItems().add(item.get());
            }
            return Optional.of(order);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get order: ", e);
        }
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        String query = "UPDATE orders SET user_id = ? WHERE order_id = ?;";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setLong(1, order.getUserId());
            stmt.setLong(1, order.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update order: ", e);
        }
        return order;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String queryItems = "DELETE FROM orders_items WHERE order_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(queryItems)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete order: ", e);
        }
        String query = "DELETE FROM orders WHERE order_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete order: ", e);
        }
        return true;
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        String query = "SELECT * FROM orders;";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement stmt
                     = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long orderId = rs.getLong("order_id");
                Order order = get(orderId).get();
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all orders: ", e);
        }
        return orders;
    }

    @Override
    public Order addItem(Long orderId, Long itemId) throws DataProcessingException {
        String query = "INSERT INTO `orders_items` (`order_id`, `item_id`) VALUES (?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, itemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all orders: ", e);
        }
        return get(orderId).get();
    }

    @Override
    public List<Order> getOrders(Long userId) throws DataProcessingException {
        String query = "SELECT order_id FROM orders WHERE user_id = ?;";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long orderId = resultSet.getLong("order_id");
                Optional<Order> order = get(orderId);
                orders.add(order.get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user orders: ", e);
        }
        return orders;
    }
}
