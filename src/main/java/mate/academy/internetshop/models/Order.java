package mate.academy.internetshop.models;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.idgenerator.IdGenerator;

public class Order {
    private Long id;
    private Long userId;
    private List<Item> items = new ArrayList<>();

    public Order(List<Item> items, Long userId) {
        this.id = IdGenerator.getOrderId();
        this.userId = userId;
        this.items = items;
    }

    public Order() {
    }

    public Long getId() {
        return id;
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
        return "Order{"
                + "id=" + id
                + ", userId=" + userId
                + ", items=" + items
                + '}';
    }
}
