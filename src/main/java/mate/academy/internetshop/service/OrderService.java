package mate.academy.internetshop.service;

import java.util.List;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.User;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(List<Item> items, User user) throws DataProcessingException;

    List getUserOrders(User user) throws DataProcessingException;

    List<Order> getAll() throws DataProcessingException;
}
