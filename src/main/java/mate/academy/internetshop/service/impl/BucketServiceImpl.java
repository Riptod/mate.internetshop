package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.Optional;
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
        Bucket newBucket = bucketDao.get(bucket.getId()).get();
        newBucket.getItems().add(item);
        bucketDao.update(newBucket);
    }

    @Override
    public Optional<Bucket> getByUser(Long userId) {
        Optional<Bucket> bucket = bucketDao.getByUser(userId);
        return bucket;
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        Bucket newBucket = bucketDao.get(bucket.getId()).get();
        List<Item> itemOfBucket = newBucket.getItems();
        itemOfBucket.remove(item);
        bucketDao.update(newBucket);
    }

    @Override
    public Bucket clear(Long bucketId) {
        Bucket bucket = bucketDao.get(bucketId).get();
        bucket.getItems().clear();
        return bucket;
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucketDao.get(bucket.getId()).get().getItems();
    }

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Optional<Bucket> get(Long id) {
        Optional<Bucket> bucket = bucketDao.get(id);
        return bucket;
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
