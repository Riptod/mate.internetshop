package mate.academy.internetshop.service;

import java.util.Optional;
import mate.academy.internetshop.exceptions.AuthentificationException;
import mate.academy.internetshop.models.User;

public interface UserService  extends GenericService<User, Long> {
    User login(String login, String password) throws AuthentificationException;

    Optional<User> getByToken(String token);
}
