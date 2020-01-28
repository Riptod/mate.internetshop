package mate.academy.internetshop.models;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.idgenerator.IdGenerator;

public class Bucket {
    private Long id;
    private Long userId;
    private List<Item> items;

    public Bucket() {
        this.id = IdGenerator.getBucketId();
        items = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void clearItems() {
        items.clear();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Bucket{"
                + "id=" + id
                + ", userId=" + userId
                + ", items=" + items
                + '}';
    }
}
