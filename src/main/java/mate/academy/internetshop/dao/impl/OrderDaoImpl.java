package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.database.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.Order;

@Dao
public class OrderDaoImpl implements OrderDao {
    @Override
    public Order create(Order item) {
        Storage.orders.add(item);
        return item;
    }

    @Override
    public Optional<Order> get(Long id) {
        return Optional.ofNullable(Storage.orders.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Can't find number with id " + id)));
    }

    @Override
    public Order update(Order item) {
        for (int i = 0; i < Storage.orders.size(); i++) {
            if (Storage.orders.get(i).getId().equals(item.getId())) {
                Storage.orders.set(i, item);
                return item;
            }
        }
        throw new NoSuchElementException("Can't find item" + item.getId());
    }

    @Override
    public boolean delete(Long id) {
        Optional<Order> optionalOrder = Optional.ofNullable(Storage.orders
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().get());
        if (optionalOrder.isPresent()) {
            Storage.orders.remove(optionalOrder.get());
            return Storage.orders.remove(optionalOrder.get());
        }
        return false;
    }

    @Override
    public Order deleteByObj(Order object) {
        Storage.orders.removeIf(b -> b.equals(object));
        return object;
    }

    @Override
    public List<Order> getAll() {
        return Storage.orders;
    }

}
