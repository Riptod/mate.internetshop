package mate.academy.internetshop.dao.jbbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.Role;
import mate.academy.internetshop.models.User;

@Dao
public class UserDaoJdbcImpl extends AbstractDao<User> implements UserDao {

    @Inject
    private static OrderDao orderDao;

    public UserDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE token = ?;";
        Optional<User> user;
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                user = get(userId);
                return user;
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: ", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        String query = "SELECT * FROM users;";
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String token = resultSet.getString("token");
                User user = setUser(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get all users: ", e);
        }
        return users;
    }

    private Optional<User> addRole(Long userId, Long roleId) throws DataProcessingException {
        String query = "INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES (?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, roleId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to add role: ", e);
        }
        return get(userId);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        String query = "INSERT INTO `users` (`name`, `surname`, `login`, `password`, `token`)"
                + " VALUES (?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                while (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create user: ", e);
        }

        for (Role role : user.getRoles()) {
            addRole(user.getId(), role.getId());
        }
        return user;
    }

    @Override
    public Optional<User> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM users INNER JOIN users_roles"
                + " USING(user_id) WHERE user_id = ?;";
        User user = new User();
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String token = resultSet.getString("token");
                Long roleId = resultSet.getLong("role_id");
                RoleDao roleDao = new RoleDaoJdbcImpl(connection);
                Optional<Role> role = roleDao.get(roleId);
                user = setUser(resultSet);
                user.addRole(role.get());
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: ", e);
        }
        return Optional.empty();
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?"
                + " WHERE user_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update user: ", e);
        }
        return user;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM users WHERE user_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete user: ", e);
        }
        return true;
    }

    private User setUser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setToken(resultSet.getString("token"));
        return user;
    }

    @Override
    public Optional<User> login(String login, String password) throws DataProcessingException {
        String query = "SELECT * FROM users WHERE login = ? AND password = ?;";
        Optional<User> user;
        Long userId = null;
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                userId = resultSet.getLong("user_id");
            }
            user = get(userId);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to login user: ", e);
        }
        return user;
    }

    @Override
    public List<Order> getOrders(Long userId) throws DataProcessingException {
        String query = "SELECT order_id FROM orders WHERE user_id = ?;";
        List<Order> orders = new ArrayList<>();
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long orderId = resultSet.getLong("order_id");
                Optional<Order> order = orderDao.get(orderId);
                orders.add(order.get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user orders: ", e);
        }
        return orders;
    }
}
