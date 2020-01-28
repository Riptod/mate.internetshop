package mate.academy.internetshop.dao;

import java.util.List;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Order;

public interface OrderDao extends GenericDao<Order, Long> {

    public List<Order> getAll() throws DataProcessingException;

    public Order addItem(Long orderId, Long itemId) throws DataProcessingException;
}
