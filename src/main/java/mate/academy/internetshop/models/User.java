package mate.academy.internetshop.models;

import java.util.List;
import mate.academy.internetshop.idgenerator.IdGenerator;

public class User {
    private Long id;
    private String name;
    private List<Order> orders;
    private Bucket bucket;

    public User(String name) {
        this.id = IdGenerator.getUserId();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Bucket getBucket() {
        return bucket;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", orders=" + orders
                + ", bucket=" + bucket + '}';
    }
}
