package mate.academy.internetshop.dao;

import java.util.Optional;

import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Bucket;

public interface BucketDao extends GenericDao<Bucket, Long> {
    Optional<Bucket> getByUser(Long userId) throws DataProcessingException;

    public Optional<Bucket> deleteItem(Long bucketId, Long itemId) throws DataProcessingException;

    public Optional<Bucket> addItem(Long bucketId, Long itemId) throws DataProcessingException;

    public Optional<Bucket> clear(Long bucketId) throws DataProcessingException;
}
