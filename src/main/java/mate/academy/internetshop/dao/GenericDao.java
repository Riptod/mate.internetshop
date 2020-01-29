package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.exceptions.DataProcessingException;

public interface GenericDao<T, I> {
    T create(T object) throws DataProcessingException;

    Optional<T> get(I id) throws DataProcessingException;

    T update(T object) throws DataProcessingException;

    boolean delete(I id) throws DataProcessingException;
}
