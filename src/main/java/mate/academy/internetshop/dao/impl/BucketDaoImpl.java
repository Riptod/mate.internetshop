package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.database.Storage;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.models.Bucket;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        return Optional.ofNullable(Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(()
                        -> new NoSuchElementException("Can't find bucket with id: " + id)));
    }

    @Override
    public Bucket update(Bucket bucket) {
        for (int i = 0; i < Storage.buckets.size(); i++) {
            if (Storage.buckets.get(i).getId().equals(bucket.getId())) {
                Storage.buckets.set(i, bucket);
                return bucket;
            }
        }
        throw new NoSuchElementException("Can't find item" + bucket.getId());
    }

    @Override
    public boolean delete(Long id) {
        Optional<Bucket> optionalBucket = Optional.ofNullable(Storage.buckets
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst().get());
        if (optionalBucket.isPresent()) {
            Storage.buckets.remove(optionalBucket.get());
            return Storage.buckets.remove(optionalBucket.get());
        }
        return false;
    }

    @Override
    public Bucket deleteByObj(Bucket object) {
        Storage.buckets.removeIf(b -> b.equals(object));
        return object;
    }

    @Override
    public List<Bucket> getAll() {
        return Storage.buckets;
    }

    @Override
    public Optional<Bucket> getByUser(Long userId) {
        return Optional.ofNullable(Storage.buckets
                .stream()
                .filter(b -> b.getUserId().equals(userId))
                .findFirst()
                .orElseThrow(()
                        -> new NoSuchElementException("Can't find bucket with id: " + userId)));
    }
}
