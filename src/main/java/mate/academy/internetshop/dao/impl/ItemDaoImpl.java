package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.database.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.Item;

@Dao
public class ItemDaoImpl implements ItemDao {
    @Override
    public Item create(Item item) {
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) {
        for (int i = 0; i < Storage.items.size(); i++) {
            if (Storage.items.get(i).getId().equals(item.getId())) {
                Storage.items.set(i, item);
                return item;
            }
        }
        throw new NoSuchElementException("Can't find item" + item.getName());
    }

    @Override
    public boolean delete(Long id) {
        Optional<Item> optionalItem = Optional.ofNullable(Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().get());
        if (optionalItem.isPresent()) {
            Storage.items.remove(optionalItem.get());
            return Storage.items.remove(optionalItem.get());
        }
        return false;
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
