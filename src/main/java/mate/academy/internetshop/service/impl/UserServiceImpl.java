package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.AuthentificationException;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Inject
    private static UserDao userDao;

    @Override
    public List<Order> getOrders(Long userId) throws DataProcessingException {
        return userDao.getOrders(userId);
    }

    @Override
    public User create(User user) throws DataProcessingException {
        user.setToken(getToken());
        return userDao.create(user);
    }

    private String getToken() {
        return UUID.randomUUID().toString();
    }

    @Override
    public User get(Long id) throws DataProcessingException {
        return userDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Found no user with id " + id));
    }

    @Override
    public User update(User user) throws DataProcessingException {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        return userDao.delete(id);
    }

    @Override
    public List<User> getAll() throws DataProcessingException {
        return userDao.getAll();
    }

    @Override
    public User login(String login, String password) throws AuthentificationException,
            DataProcessingException {
        Optional<User> user = userDao.login(login, password);
        if (user.isEmpty() || !user.get().getPassword().equals(password)) {
            throw new AuthentificationException("Login, or password incorrect");
        }
        return  user.get();
    }

    @Override
    public Optional<User> getByToken(String token) throws DataProcessingException {
        return userDao.getByToken(token);
    }
}
