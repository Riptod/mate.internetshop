package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
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
    public Bucket addItem(Bucket bucket, Item item) throws DataProcessingException {
        return bucketDao.addItem(bucket.getId(), item.getId())
                .orElseThrow(() -> new NoSuchElementException("Found no item with id "
                        + item.getId()));
    }

    @Override
    public Bucket getByUser(Long userId) throws DataProcessingException {
        return bucketDao.getByUser(userId)
                .orElseThrow(() -> new NoSuchElementException("Found no bucket with id " + userId));
    }

    @Override
    public Bucket deleteItem(Bucket bucket, Item item) throws DataProcessingException {
        return bucketDao.deleteItem(bucket.getId(), item.getId())
                .orElseThrow(() -> new NoSuchElementException("Found no item with id "
                        + item.getId()));
    }

    @Override
    public Bucket clear(Long bucketId) throws DataProcessingException {
       return bucketDao.clear(bucketId)
               .orElseThrow(() -> new NoSuchElementException("Found no bucket with id "
                       + bucketId));
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) throws DataProcessingException {
        Bucket newBucket = bucketDao.get(bucket.getId())
                .orElseThrow(() -> new NoSuchElementException("Found no bucket with id "
                        + bucket.getId()));
        return newBucket.getItems();
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) throws DataProcessingException {
        return bucketDao.get(id)
                .orElseThrow(() -> new NoSuchElementException("Found no bucket with id " + id));
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        return bucketDao.delete(id);
    }
}
