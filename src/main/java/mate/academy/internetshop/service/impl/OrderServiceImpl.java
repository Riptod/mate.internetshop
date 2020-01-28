package mate.academy.internetshop.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.OrderDao;
import mate.academy.internetshop.dao.UserDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    @Inject
    private static OrderDao orderDao;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static UserDao userDao;

    @Override
    public Order completeOrder(List<Item> items, User user) throws DataProcessingException {
        Order order = new Order(items, user.getId());
        bucketService.clear(bucketService.getByUser(user.getId()).getId());
        return orderDao.create(order);
    }

    @Override
    public List getUserOrders(User user) throws DataProcessingException {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orderDao.getAll()) {
            if (order.getUserId().equals(user.getId())) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    @Override
    public Order create(Order order) throws DataProcessingException {
        return orderDao.create(order);
    }

    @Override
    public Order get(Long id) throws DataProcessingException {
        return orderDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Found no order with id " + id));
    }

    @Override
    public Order update(Order order) throws DataProcessingException {
        return orderDao.update(order);
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        return orderDao.delete(id);
    }

    @Override
    public List<Order> getAll() throws DataProcessingException {
        return orderDao.getAll();
    }


}
