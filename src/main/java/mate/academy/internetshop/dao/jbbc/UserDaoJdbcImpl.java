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
        String query = "SELECT * FROM users INNER JOIN users_roles"
                + " USING(user_id) WHERE token = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = setUser(resultSet);
            return Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: ", e);
        }
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        String query = "SELECT * FROM users;";
        List<User> users = new ArrayList<>();
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                long userId = resultSet.getLong("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String login = resultSet.getString("login");
                String password = resultSet.getString("password");
                String token = resultSet.getString("token");
                byte[] salt = resultSet.getBytes("salt");
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
        String query = "INSERT INTO `users` (`name`, `surname`, `login`, `password`, `token`, `salt`)"
                + " VALUES (?, ?, ?, ?, ?, ?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getToken());
            preparedStatement.setBytes(6, user.getSalt());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = setUser(resultSet);
            return Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get user: ", e);
        }
    }

    @Override
    public User update(User user) throws DataProcessingException {
        String query = "UPDATE users SET name = ?, surname = ?, login = ?, password = ?, salt = ?"
                + " WHERE user_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setBytes(5, user.getSalt());
            preparedStatement.setLong(6, user.getId());
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

    private User setUser(ResultSet resultSet) throws SQLException,
            DataProcessingException {
        User user = new User();
        if (resultSet.next()) {
            user.setId(resultSet.getLong("user_id"));
            user.setName(resultSet.getString("name"));
            user.setSurname(resultSet.getString("surname"));
            user.setLogin(resultSet.getString("login"));
            user.setPassword(resultSet.getString("password"));
            user.setToken(resultSet.getString("token"));
            user.setSalt(resultSet.getBytes("salt"));
            Long roleId = resultSet.getLong("role_id");
            RoleDao roleDao = new RoleDaoJdbcImpl(connection);
            Optional<Role> role = roleDao.get(roleId);
            user.addRole(role.get());
        }
        return user;
    }

    @Override
    public Optional<User> login(String login) throws DataProcessingException {
        String query = "SELECT * FROM users INNER JOIN users_roles"
                + " USING(user_id) WHERE login = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            User user = setUser(resultSet);
            return Optional.of(user);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to login user: ", e);
        }
    }
}
