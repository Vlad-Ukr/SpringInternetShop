DROP TABLE IF EXISTS `products_orders`;

DROP TABLE IF EXISTS `orders`;

DROP TABLE IF EXISTS `products`;

DROP TABLE IF EXISTS `product_categories`;

DROP TABLE IF EXISTS `product_manufacturer`;

DROP TABLE IF EXISTS `role`;

DROP TABLE IF EXISTS `user_ban_schedule`;

DROP TABLE IF EXISTS `users_roles`;

DROP TABLE IF EXISTS `user_role`;

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users`
(
    `userID`          varchar(45)  NOT NULL,
    `email`           varchar(256) NOT NULL,
    `name`            varchar(45)  NOT NULL,
    `surname`         varchar(45)  NOT NULL,
    `password`        varchar(256) NOT NULL,
    `role_id`         int          NOT NULL DEFAULT '1',
    `ban_date`        timestamp    NOT NULL,
    `failed_attempts` int          NOT NULL DEFAULT '0',
    PRIMARY KEY (`userID`),
    UNIQUE KEY `userID_UNIQUE` (`userID`),
    UNIQUE KEY `login_UNIQUE` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `user_role`
(
    `user_role_id` int NOT NULL DEFAULT '1',
    `role`         varchar(45)  DEFAULT NULL,
    `id`           int NOT NULL DEFAULT '0',
    PRIMARY KEY (`user_role_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `users_roles`
(
    `user_id` varchar(45) DEFAULT NULL,
    `role_id` int         DEFAULT '1',
    KEY `fk15_idx` (`user_id`),
    KEY `fk16_idx` (`role_id`),
    CONSTRAINT `fk15` FOREIGN KEY (`user_id`) REFERENCES `users` (`userID`) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT `fk16` FOREIGN KEY (`role_id`) REFERENCES `user_role` (`user_role_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `user_ban_schedule`
(
    `user_id`         varchar(45) NOT NULL,
    `ban_date`        timestamp   NOT NULL,
    `failed_attempts` int         NOT NULL DEFAULT '0',
    PRIMARY KEY (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `product_manufacturer`
(
    `product_manufacturerID` int         NOT NULL,
    `manufacturerName`       varchar(45) NOT NULL,
    `manufacturer_name`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`product_manufacturerID`),
    UNIQUE KEY `manufacturerID_UNIQUE` (`product_manufacturerID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `product_categories`
(
    `product_categoryID` int         NOT NULL,
    `categoryName`       varchar(45) NOT NULL,
    `category_name`      varchar(255) DEFAULT NULL,
    PRIMARY KEY (`product_categoryID`),
    UNIQUE KEY `categoryType_UNIQUE` (`categoryName`),
    UNIQUE KEY `product_categoryID_UNIQUE` (`product_categoryID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `products`
(
    `productID`      varchar(45) NOT NULL,
    `categoryID`     int         NOT NULL,
    `name`           varchar(45) NOT NULL,
    `price`          float       NOT NULL,
    `quantity`       int         NOT NULL DEFAULT '0',
    `description`    longtext    NOT NULL,
    `manufacturerID` int         NOT NULL,
    PRIMARY KEY (`productID`),
    UNIQUE KEY `productID_UNIQUE` (`productID`),
    KEY `fk1_idx` (`categoryID`),
    KEY `fk2_idx` (`manufacturerID`),
    CONSTRAINT `fk1` FOREIGN KEY (`categoryID`) REFERENCES `product_categories` (`product_categoryID`),
    CONSTRAINT `fk2` FOREIGN KEY (`manufacturerID`) REFERENCES `product_manufacturer` (`product_manufacturerID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `orders`
(
    `orderID`         varchar(45) NOT NULL,
    `order_status`    int         NOT NULL,
    `order_detailing` varchar(255) DEFAULT NULL,
    `order_date`      timestamp   NOT NULL,
    `user_id`         varchar(45) NOT NULL,
    PRIMARY KEY (`orderID`),
    KEY `fk1_idx` (`user_id`),
    CONSTRAINT `fk3` FOREIGN KEY (`user_id`) REFERENCES `users` (`userID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;

CREATE TABLE `products_orders`
(
    `id`                     varchar(45) NOT NULL,
    `name`                   varchar(45) NOT NULL,
    `product_id`             varchar(45) NOT NULL,
    `order_id`               varchar(45) NOT NULL,
    `ordered_product_amount` int         NOT NULL,
    `price_by_order_date`    double      NOT NULL,
    PRIMARY KEY (`id`),
    KEY `fk4_idx` (`product_id`),
    KEY `fk5_idx` (`order_id`),
    CONSTRAINT `fk4` FOREIGN KEY (`product_id`) REFERENCES `products` (`productID`),
    CONSTRAINT `fk5` FOREIGN KEY (`order_id`) REFERENCES `orders` (`orderID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb3;
