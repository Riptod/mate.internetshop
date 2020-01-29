package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Role;

public interface RoleDao {
    Role create(Role role) throws DataProcessingException;

    Optional<Role> get(Long id) throws DataProcessingException;

    Role update(Role role) throws DataProcessingException;

    boolean delete(Long id) throws DataProcessingException;
}
