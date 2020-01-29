package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.exceptions.AuthentificationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.User;

public interface UserService  extends GenericService<User, Long> {
    User login(String login, String password) throws AuthentificationException,
            DataProcessingException;

    Optional<User> getByToken(String token) throws DataProcessingException;

    public List<User> getAll() throws DataProcessingException;

}
