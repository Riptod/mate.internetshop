package mate.academy.internetshop.dao.jbbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.exceptions.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.models.Bucket;
import mate.academy.internetshop.models.Item;

@Dao
public class BucketDaoJdbcImpl extends AbstractDao<Bucket> implements BucketDao {

    @Inject
    private static ItemDao itemDao;

    public BucketDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Optional<Bucket> getByUser(Long userId) throws DataProcessingException {
        String query = "SELECT * FROM bucket WHERE user_id = ?;";
        Bucket bucket = new Bucket();
        try (PreparedStatement stmt
                     = connection.prepareStatement(query)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long bucketIdFromDb = rs.getLong("bucket_id");
                long userIdFromDb = rs.getLong("user_id");
                bucket.setId(bucketIdFromDb);
                bucket.setUserId(userIdFromDb);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get bucket: ", e);
        }
        String queryItems = "SELECT item_id from bucket_items WHERE bucket_id = ?;";
        try (PreparedStatement stmt
                     = connection.prepareStatement(queryItems)) {
            stmt.setLong(1, bucket.getId());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long itemId = rs.getLong("item_id");
                Optional<Item> item = itemDao.get(itemId);
                bucket.getItems().add(item.get());
            }
            return Optional.of(bucket);
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get bucket: ", e);
        }
    }

    @Override
    public Bucket create(Bucket bucket) throws DataProcessingException {
        String query = "INSERT INTO `bucket` (`user_id`) VALUES (?);";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, bucket.getUserId());
            stmt.executeUpdate();
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    bucket.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating bucket failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to create bucket: ", e);
        }

        for (Item item : bucket.getItems()) {
            addItem(bucket.getId(), item.getId());
        }
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) throws DataProcessingException {
        String query = "SELECT bucket_id, user_id, item_id FROM bucket INNER JOIN bucket_items"
                + " USING (bucket_id) WHERE bucket_id = ?;";
        Bucket bucket = new Bucket();
        try (PreparedStatement stmt
                     = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                long bucketId = rs.getLong("bucket_id");
                long userId = rs.getLong("user_id");
                long itemId = rs.getLong("item_id");
                bucket.setId(bucketId);
                bucket.setUserId(userId);
                Optional<Item> item = itemDao.get(itemId);
                bucket.getItems().add(item.get());
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to get bucket: ", e);
        }
        return Optional.of(bucket);
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        String query = "UPDATE bucket SET user_id = ? WHERE bucket_id = ?;";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query)) {
            stmt.setLong(1, bucket.getUserId());
            stmt.setLong(2, bucket.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to update bucket: ", e);
        }
        return bucket;
    }

    @Override
    public boolean delete(Long id) throws DataProcessingException {
        String query = "DELETE FROM bucket WHERE bucket_id = ?;";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete bucket: ", e);
        }
        clear(id);
        return true;
    }

    public Optional<Bucket> addItem(Long bucketId, Long itemId) throws DataProcessingException {
        String query = "INSERT INTO `bucket_items` (`bucket_id`, `item_id`) VALUES (?, ?);";
        try (PreparedStatement stmt
                     = connection.prepareStatement(query)) {
            stmt.setLong(1, bucketId);
            stmt.setLong(2, itemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to add item to  bucket: ", e);
        }
        return get(bucketId);
    }

    @Override
    public Optional<Bucket> deleteItem(Long bucketId, Long itemId) throws DataProcessingException {
        String query = "DELETE FROM bucket_items WHERE bucket_id = ? AND item_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucketId);
            preparedStatement.setLong(2, itemId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to delete item from bucket: ", e);
        }
        return get(bucketId);
    }

    @Override
    public Optional<Bucket> clear(Long bucketId) throws DataProcessingException {
        String query = "DELETE FROM bucket_items WHERE bucket_id = ?;";
        try (PreparedStatement preparedStatement
                     = connection.prepareStatement(query)) {
            preparedStatement.setLong(1, bucketId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Failed to clear bucket: ", e);
        }
        return get(bucketId);
    }
}
