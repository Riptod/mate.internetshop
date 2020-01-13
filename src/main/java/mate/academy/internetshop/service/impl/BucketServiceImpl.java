package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.models.Bucket;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private static BucketDao bucketDao;

    @Inject
    private static ItemDao itemDao;

    @Override
    public void addItem(Bucket bucket, Item item) {
        Bucket newBucket = get(bucket.getId());
        newBucket.getItems().add(item);
        bucketDao.update(newBucket);
    }

    @Override
    public Bucket getByUser(Long userId) {
        return bucketDao.getByUser(userId)
                .orElseThrow(() -> new NoSuchElementException("Found no bucket with id " + userId));
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket newBucket = get(bucket.getId());
        List<Item> itemOfBucket = newBucket.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(newBucket);
    }

    @Override
    public Bucket clear(Long bucketId) {
        Bucket bucket = get(bucketId);
        bucket.getItems().clear();
        return bucket;
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return get(bucket.getId()).getItems();
    }

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Found no bucket with id " + id));
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Long id) {
        return bucketDao.delete(id);
    }

    @Override
    public List<Bucket> getAll() {
        return bucketDao.getAll();
    }
}
