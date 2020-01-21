package mate.academy.internetshop.idgenerator;

public class IdGenerator {
    private static Long userId = 0L;
    private static Long bucketId = 0L;
    private static Long orderId = 0L;
    private static Long itemId = 0L;
    private static Long roleId = 0L;

    public static Long getUserId() {
        userId++;
        return userId;
    }

    public static Long getBucketId() {
        bucketId++;
        return bucketId;
    }

    public static Long getOrderId() {
        orderId++;
        return orderId;
    }

    public static Long getItemId() {
        itemId++;
        return itemId;
    }

    public static Long getRoleId() {
        roleId++;
        return roleId;
    }
}
