CREATE TABLE `users`
(
    `user_id`  int            NOT NULL AUTO_INCREMENT,
    `name`     varchar(45)  DEFAULT NULL,
    `surname`  varchar(45)  DEFAULT NULL,
    `login`    varchar(45)    NOT NULL,
    `password` varchar(255)   NOT NULL,
    `token`    varchar(255) DEFAULT NULL,
    `salt`     varbinary(255) NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 24
  DEFAULT CHARSET = utf8;

CREATE TABLE `roles`
(
    `roles_id` int         NOT NULL AUTO_INCREMENT,
    `role`     varchar(45) NOT NULL,
    PRIMARY KEY (`roles_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

CREATE TABLE `users_roles`
(
    `user_roles_id` int NOT NULL AUTO_INCREMENT,
    `role_id`       int NOT NULL,
    `user_id`       int NOT NULL,
    PRIMARY KEY (`user_roles_id`),
    KEY `user_roles_user_fk_idx` (`user_id`),
    KEY `user_roles_roles_fk_idx` (`role_id`),
    CONSTRAINT `user_roles_roles_fk` FOREIGN KEY (`role_id`) REFERENCES `roles` (`roles_id`),
    CONSTRAINT `user_roles_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 18
  DEFAULT CHARSET = utf8;

CREATE TABLE `bucket`
(
    `bucket_id` int NOT NULL AUTO_INCREMENT,
    `user_id`   int NOT NULL,
    PRIMARY KEY (`bucket_id`),
    KEY `bucket_user_fk_idx` (`user_id`),
    CONSTRAINT `bucket_user_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 3
  DEFAULT CHARSET = utf8;

CREATE TABLE `items`
(
    `item_id` int           NOT NULL AUTO_INCREMENT,
    `name`    varchar(255)  NOT NULL,
    `price`   decimal(6, 2) NOT NULL,
    PRIMARY KEY (`item_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 4
  DEFAULT CHARSET = utf8;

CREATE TABLE `bucket_items`
(
    `bucket_items_id` int NOT NULL AUTO_INCREMENT,
    `bucket_id`       int NOT NULL,
    `item_id`         int NOT NULL,
    PRIMARY KEY (`bucket_items_id`),
    KEY `bucket_items_bucket_fk_idx` (`bucket_id`),
    KEY `bucket_items_itemst_fk_idx` (`item_id`),
    CONSTRAINT `bucket_items_bucket_fk` FOREIGN KEY (`bucket_id`) REFERENCES `bucket` (`bucket_id`),
    CONSTRAINT `bucket_items_itemst_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 44
  DEFAULT CHARSET = utf8;


CREATE TABLE `orders`
(
    `order_id` int NOT NULL AUTO_INCREMENT,
    `user_id`  int NOT NULL,
    PRIMARY KEY (`order_id`),
    KEY `orders_users_fk_idx` (`user_id`),
    CONSTRAINT `orders_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 7
  DEFAULT CHARSET = utf8;

CREATE TABLE `orders_items`
(
    `orders_items_id` int NOT NULL AUTO_INCREMENT,
    `order_id`        int NOT NULL,
    `item_id`         int NOT NULL,
    PRIMARY KEY (`orders_items_id`),
    KEY `orders_items_orders_fk_idx` (`order_id`),
    KEY `orders_items_items_fk_idx` (`item_id`),
    CONSTRAINT `orders_items_items_fk` FOREIGN KEY (`item_id`) REFERENCES `items` (`item_id`),
    CONSTRAINT `orders_items_orders_fk` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 12
  DEFAULT CHARSET = utf8;

INSERT INTO `items` (`name`, `price`) VALUES ('Samsung', '900');
INSERT INTO `items` (`name`, `price`) VALUES ('Xiaomi', '1200');
