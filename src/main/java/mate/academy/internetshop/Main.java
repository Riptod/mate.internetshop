package mate.academy.internetshop;

import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.models.Bucket;
import mate.academy.internetshop.models.Item;
import mate.academy.internetshop.models.User;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ItemService;
import mate.academy.internetshop.service.OrderService;
import mate.academy.internetshop.service.UserService;

public class Main {

    static {
        try {
            Injector.injectDependency();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Inject
    private static ItemService itemService;

    @Inject
    private static OrderService orderService;

    @Inject
    private static BucketService bucketService;

    @Inject
    private static UserService userService;

    public static void main(String[] args) {
        Item firstItem = new Item("firts", 2.12);
        Item secondItem = new Item("second", 3.92);
        Item thirdItem = new Item("third", 3.92);
        itemService.create(firstItem);
        itemService.create(secondItem);
        itemService.create(thirdItem);

        User user = new User("Vasya");
        userService.create(user);
        User user2 = new User("Dima");
        userService.create(user2);
        Bucket bucket1 = new Bucket();
        bucketService.create(bucket1);
        bucket1.setUserId(user.getId());
        Bucket bucket2 = new Bucket();
        bucket2.setUserId(user2.getId());
        bucketService.create(bucket2);
        bucketService.addItem(bucket2, firstItem);
        bucketService.addItem(bucket1, firstItem);
        bucketService.addItem(bucket1, secondItem);
        bucketService.addItem(bucket1, thirdItem);

        orderService.completeOrder(bucketService.getAllItems(bucket1), user);

        System.out.println(bucketService.getAllItems(bucket1));
        System.out.println();
        System.out.println(bucketService.getAll());
    }
}

