package mate.academy.internetshop.dao.jbbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import mate.academy.internetshop.dao.RoleDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.Role;

@Dao
public class RoleDaoJdbcImpl extends AbstractDao<Role> implements RoleDao {

    public RoleDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Role create(Role role) throws DataProcessingException {
        String query = "INSERT INTO roles (role) VALUES (?);";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.executeUpdate();
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    role.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating role failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create role: ", e);
        }
        return role;
    }

    @Override
    public Optional<Role> get(Long id) throws DataProcessingException {
        String query = "SELECT * FROM roles WHERE roles_id = ?;";
        Role role = new Role();
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long roleId = resultSet.getLong("roles_id");
                String name = resultSet.getString("role");
                role.setId(roleId);
                role.setRoleName(name);
                return Optional.of(role);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get role: ", e);
        }
        return Optional.empty();
    }

    @Override
    public Role update(Role role) throws DataProcessingException {
        String query = "UPDATE roles SET role = ? WHERE roles_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setString(1, role.getRoleName());
            preparedStatement.setLong(2, role.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update role: ", e);
        }
        return role;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM roles WHERE roles_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete role: ", e);
        }
        return false;
    }
}
