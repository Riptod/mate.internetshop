package mate.academy.internetshop.dao;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Item;

import java.util.List;

public interface ItemDao extends GenericDao<Item, Long> {

    public List<Item> getAll() throws DataProcessingException;
}
