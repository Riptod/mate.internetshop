package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

public interface GenericService<T, I> {
    T create(T object);

    Optional<T> get(I id);

    T update(T object);

    boolean delete(I id);

    List<T> getAll();
}
