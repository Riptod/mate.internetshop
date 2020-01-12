package mate.academy.internetshop.dao;

import java.util.Optional;
import mate.academy.internetshop.models.Bucket;

public interface BucketDao extends GenericDao<Bucket, Long> {
    Optional<Bucket> getByUser(Long userId);
}
