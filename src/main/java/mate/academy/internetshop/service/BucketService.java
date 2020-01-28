package mate.academy.internetshop.service;

import java.util.List;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.models.Bucket;
import mate.academy.internetshop.models.Item;

public interface BucketService extends GenericService<Bucket, Long> {
    Bucket addItem(Bucket bucket, Item item) throws DataProcessingException;

    Bucket deleteItem(Bucket bucket, Item item) throws DataProcessingException;

    Bucket clear(Long bucketId) throws DataProcessingException;

    List<Item> getAllItems(Bucket bucket) throws DataProcessingException;

    Bucket getByUser(Long userId) throws DataProcessingException;
}
