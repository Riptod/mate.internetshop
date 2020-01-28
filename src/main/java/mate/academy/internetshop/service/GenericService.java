package mate.academy.internetshop.service;

import mate.academy.internetshop.exceptions.DataProcessingException;

public interface GenericService<T, I> {

    T create(T object) throws DataProcessingException;

    T get(I id) throws DataProcessingException;

    T update(T object) throws DataProcessingException;

    boolean delete(I id) throws DataProcessingException;
}
