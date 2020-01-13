package mate.academy.internetshop.models;

import mate.academy.internetshop.idgenerator.IdGenerator;

public class Item {
    private Long id;
    private String name;
    private Double price;

    public Item(String name, Double price) {
        this.id = IdGenerator.getItemId();
        this.name = name;
        this.price = price;
    }

    public Item() {
        this.id = IdGenerator.getItemId();
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + '}';
    }
}
