package mate.academy.internetshop.service;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.models.Bucket;
import mate.academy.internetshop.models.Item;

public interface BucketService extends GenericService<Bucket, Long> {
    void addItem(Bucket bucket, Item item);

    void deleteItem(Bucket bucket, Item item);

    Bucket clear(Long bucketId);

    List getAllItems(Bucket bucket);

    Optional<Bucket> getByUser(Long userId);
}
