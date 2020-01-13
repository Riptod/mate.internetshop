package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.database.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.User;

@Dao
public class UserDaoImpl implements UserDao {

    @Override
    public User create(User user) {
        Storage.users.add(user);
        return user;
    }

    @Override
    public Optional<User> get(Long id) {
        return Storage.users
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public User update(User user) {
        for (int i = 0; i < Storage.users.size(); i++) {
            if (Storage.users.get(i).getId().equals(user.getId())) {
                Storage.users.set(i, user);
                return user;
            }
        }
        throw new NoSuchElementException("Can't find user" + user.getName());
    }

    @Override
    public boolean delete(Long id) {
        Optional<User> optionalUser = Optional.ofNullable(Storage.users
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().get());
        if (optionalUser.isPresent()) {
            Storage.users.remove(optionalUser.get());
            return Storage.users.remove(optionalUser.get());
        }
        return false;
    }

    @Override
    public User deleteByObj(User object) {
        Storage.users.removeIf(b -> b.equals(object));
        return object;
    }

    @Override
    public List<User> getAll() {
        return Storage.users;
    }

}
