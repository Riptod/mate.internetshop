CREATE SCHEMA `internetshop` DEFAULT CHARACTER SET utf8 ;

CREATE TABLE `internetshop`.`items` (
  `item_id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(6,2) NOT NULL,
  PRIMARY KEY (`item_id`));

INSERT INTO `internetshop`.`items` (`name`, `price`) VALUES ('M416', '1250');

SELECT * FROM internetshop.items WHERE item_id=1;

CREATE TABLE `internetshop`.`orders` (
    `order_id` INT NOT NULL AUTO_INCREMENT,
     PRIMARY KEY (`order_id`));

CREATE TABLE `internetshop`.`orders_items` (
                                               `orders_items_id` INT NOT NULL AUTO_INCREMENT,
                                               `order_id` INT NOT NULL,
                                               `item_id` INT NOT NULL,
                                               PRIMARY KEY (`orders_items_id`),
                                               INDEX `orders_items_orders_fk_idx` (`order_id` ASC) VISIBLE,
                                               INDEX `orders_items_items_fk_idx` (`item_id` ASC) VISIBLE,
                                               CONSTRAINT `orders_items_orders_fk`
                                                   FOREIGN KEY (`order_id`)
                                                       REFERENCES `internetshop`.`orders` (`order_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
                                               CONSTRAINT `orders_items_items_fk`
                                                   FOREIGN KEY (`item_id`)
                                                       REFERENCES `internetshop`.`items` (`item_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION);

CREATE TABLE `internetshop`.`users` (
                                        `user_id` INT NOT NULL AUTO_INCREMENT,
                                        `name` VARCHAR(45) NULL,
                                        `surname` VARCHAR(45) NULL,
                                        `login` VARCHAR(45) NOT NULL,
                                        `password` VARCHAR(45) NOT NULL,
                                        `token` VARCHAR(45) NULL,
                                        PRIMARY KEY (`user_id`));

ALTER TABLE `internetshop`.`orders`
    ADD COLUMN `user_id` INT NOT NULL AFTER `order_id`,
    ADD INDEX `orders_users_fk_idx` (`user_id` ASC) VISIBLE;
;
ALTER TABLE `internetshop`.`orders`
    ADD CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internetshop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `internetshop`.`roles` (
                                        `roles_id` INT NOT NULL AUTO_INCREMENT,
                                        `role` VARCHAR(45) NOT NULL,
                                        PRIMARY KEY (`roles_id`));


CREATE TABLE `internetshop`.`user_roles` (
                                             `user_roles_id` INT NOT NULL AUTO_INCREMENT,
                                             `role_id` INT NOT NULL,
                                             `user_id` INT NOT NULL,
                                             PRIMARY KEY (`user_roles_id`),
                                             INDEX `user_roles_user_fk_idx` (`user_id` ASC) VISIBLE,
                                             INDEX `user_roles_roles_fk_idx` (`role_id` ASC) VISIBLE,
                                             CONSTRAINT `user_roles_user_fk`
                                                 FOREIGN KEY (`user_id`)
                                                     REFERENCES `internetshop`.`users` (`user_id`)
                                                     ON DELETE NO ACTION
                                                     ON UPDATE NO ACTION,
                                             CONSTRAINT `user_roles_roles_fk`
                                                 FOREIGN KEY (`role_id`)
                                                     REFERENCES `internetshop`.`roles` (`roles_id`)
                                                     ON DELETE NO ACTION
                                                     ON UPDATE NO ACTION);

CREATE TABLE `internetshop`.`bucket` (
                                         `bucket_id` INT NOT NULL AUTO_INCREMENT,
                                         `user_id` INT NOT NULL,
                                         PRIMARY KEY (`bucket_id`));

CREATE TABLE `internetshop`.`bucket_items` (
                                               `bucket_items_id` INT NOT NULL AUTO_INCREMENT,
                                               `bucket_id` INT NOT NULL,
                                               `item_id` INT NOT NULL,
                                               PRIMARY KEY (`bucket_items_id`),
                                               INDEX `bucket_items_bucket_fk_idx` (`bucket_id` ASC) VISIBLE,
                                               INDEX `bucket_items_itemst_fk_idx` (`item_id` ASC) VISIBLE,
                                               CONSTRAINT `bucket_items_bucket_fk`
                                                   FOREIGN KEY (`bucket_id`)
                                                       REFERENCES `internetshop`.`bucket` (`bucket_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
                                               CONSTRAINT `bucket_items_itemst_fk`
                                                   FOREIGN KEY (`item_id`)
                                                       REFERENCES `internetshop`.`items` (`item_id`)
                                                       ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION);

