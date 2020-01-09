package mate.academy.internetshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;

    @Inject
    private static UserDao userDao;

    @Override
    public Order completeOrder(List<Item> items, User user) {
        Order order = new Order(items, user.getId());
        return create(order);
    }

    @Override
    public List getUserOrders(User user) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orderDao.getAll()) {
            if (order.getUserId().equals(user.getId())) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public Order create(Order order) {
        return orderDao.create(order);
    }

    @Override
    public Optional<Order> get(Long id) {
        return orderDao.get(id);
    }

    @Override
    public Order update(Order order) {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long id) {
        return orderDao.delete(id);
    }

    @Override
    public List<Order> getAll() {
        return orderDao.getAll();
    }
}
