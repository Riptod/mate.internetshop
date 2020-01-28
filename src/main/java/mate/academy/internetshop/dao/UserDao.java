package mate.academy.internetshop.dao;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.User;

public interface UserDao extends GenericDao<User, Long> {

    Optional<User> getByToken(String token) throws DataProcessingException;

    public List<User> getAll() throws DataProcessingException;

    public Optional<User> login(String login, String password) throws DataProcessingException;

    public List<Order> getOrders(Long userId) throws DataProcessingException;
}
