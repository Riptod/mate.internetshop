package mate.academy.internetshop.database;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.models.Bucket;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.models.Order;
import mate.academy.internetshop.models.User;

public class Storage {
    public static final List<User> users = new ArrayList<>();
    public static final List<Item> items = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
}
