package mate.academy.internetshop.service;

import java.util.List;

public interface GenericService<T, I> {

    T create(T object);

    T get(I id);

    T update(T object);

    boolean delete(I id);

    List<T> getAll();
}
