LOCK TABLES `users` WRITE;
INSERT INTO `users`
VALUES ('38c8c816-a879-46e2-ac81-a357daf41610', 'vladunukr02@gmail.com', 'Vladyslav', 'Ukrainets',
        '$2a$10$Q3E4mNbJgMouV4zLYM8BHe7XCDhqrGoO3DE14EzSr2S2HGI07SDdO', 0, '2021-12-21 14:04:33', 0),
       ('9bd8f5fa-4a1e-44ca-9524-3d3d8440058f', 'vovkin@gmail.com', 'Vitaliy', 'Volkov',
        '$2a$10$dfSmOUQD2NnoxRmzrDsIIetwobUeJ4u3ZkvDBZ1JYYuiINLfq0JbC', 1, '2023-01-09 09:53:29', 0),
       ('b9fda7b1-0d2b-48a1-b822-38f88d224696', 'shevchenko@gmail.com', 'Yulia', 'Shevchenko',
        '$2a$10$ebLai.xzproqnK3f1lC5rOhzhJgYMvLOensrf5l32YSWzzCmVKZaW', 1, '2023-01-06 10:12:09', 0);
UNLOCK TABLES;

LOCK
    TABLES `user_role` WRITE;
INSERT INTO `user_role`
VALUES (0, 'ADMIN', 0),
       (1, 'USER', 0);
UNLOCK
    TABLES;

LOCK
    TABLES `users_roles` WRITE;
INSERT INTO `users_roles`
VALUES ('38c8c816-a879-46e2-ac81-a357daf41610', 0),
       ('38c8c816-a879-46e2-ac81-a357daf41610', 1),
       ('b9fda7b1-0d2b-48a1-b822-38f88d224696', 1),
       ('9bd8f5fa-4a1e-44ca-9524-3d3d8440058f', 1);
UNLOCK
    TABLES;

LOCK
    TABLES `user_ban_schedule` WRITE;
INSERT INTO `user_ban_schedule`
VALUES ('22ce9bb6-06fc-4367-8f3f-31f60dec32be', '2022-10-13 21:00:01', 0),
       ('27d9a64a-f1cc-4093-9bc7-04d6a687c353', '2022-10-13 21:00:00', 0),
       ('38c8c816-a879-46e2-ac81-a357daf41610', '2022-12-16 13:22:38', 0),
       ('ab08d4d8-945d-4ecb-bf21-3543961ce63d', '2022-10-17 16:00:45', 0),
       ('eaebf8b3-87d7-4a89-9bc5-6c772e0c67ab', '2022-10-15 14:17:25', 0),
       ('fa149124-d151-4ef7-b471-1bf9b9f8ee02', '2022-10-15 13:45:59', 0);
UNLOCK
    TABLES;

LOCK
    TABLES `product_manufacturer` WRITE;
INSERT INTO `product_manufacturer`
VALUES (0, 'Adidas', 'Adidas'),
       (1, 'Nike', 'Nike'),
       (2, 'DG', 'DG'),
       (3, 'LaCosta', 'LaCosta');
UNLOCK
    TABLES;

LOCK
    TABLES `product_categories` WRITE;
INSERT INTO `product_categories`
VALUES (0, 'Casual', 'Casual'),
       (1, 'Business', 'Business'),
       (2, 'Sport', 'Sport'),
       (3, 'Classic', 'Classic');
UNLOCK
    TABLES;

LOCK
    TABLES `products` WRITE;
INSERT INTO `products`
VALUES ('1', 0, 'shirt', 11, 10, 'shirt', 0),
       ('10', 1, 'f', 99, 15, 'product', 3),
       ('11', 3, 'ab', 100, 16, 'product', 2),
       ('12', 2, 'ac', 111, 16, 'product', 1),
       ('13', 1, 'ad', 124, 12, 'product', 1),
       ('14', 3, 'ac', 515, 12, 'product', 0),
       ('15', 0, 'ba', 1616, 61, 'product', 2),
       ('16', 2, 'bd', 1215, 12, 'product', 3),
       ('17', 1, 'bc', 21, 12, 'product', 0),
       ('2', 1, 'pants', 20, 20, 'pants', 1),
       ('3', 1, 'sneekers', 31, 30, 'snee', 1),
       ('4', 2, 'shorts', 92, 51, 'shoas', 2),
       ('5', 3, 'a', 44, 12, 'product', 3),
       ('6', 0, 'b', 55, 12, 'product', 0),
       ('7', 0, 'c', 66, 12, 'product', 0),
       ('8', 2, 'd', 77, 412, 'product', 1),
       ('9', 2, 'e', 88, 15, 'product', 2);
UNLOCK
    TABLES;

LOCK
    TABLES `orders` WRITE;
INSERT INTO `orders`
VALUES ('0aeb61dc-4d15-49f3-8314-ca8b30c907ad', 0, NULL, '2023-03-21 11:40:02', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('0d9b636b-0044-4851-bbc1-58bbbca47af8', 0, NULL, '2023-01-30 16:06:47', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('23b21dbc-ab15-45fa-888c-cc9a2653394b', 0, NULL, '2023-01-25 11:31:08', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('2ca73fea-5b4b-4e46-b320-1509cbd56cb7', 0, NULL, '2023-02-13 10:15:28', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('526c1b84-84bd-4cb9-93a4-871197c2711b', 0, 'Заказ почтой', '2023-02-22 13:11:21',
        '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('79188056-38c6-4486-adf1-fe64fa69edb9', 0, NULL, '2023-02-01 15:13:38', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('8da0d514-6f98-43d9-ac39-2e93e6b7c2f6', 0, NULL, '2023-02-08 15:09:30', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('9a9fdf42-9761-4956-866c-77b7ee94f227', 0, 'Please do not call after order confirm', '2023-02-01 15:24:32',
        '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('b75fa99e-84f6-425d-985a-539f1ccf2eda', 0, NULL, '2023-01-30 14:50:14', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('c6d6e30a-95c9-40d8-85ee-2a1e58457aa2', 0, NULL, '2023-02-01 15:56:11', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('ca2a2d98-83b9-4873-a6f8-b3c25a16cc6a', 0, NULL, '2023-02-08 15:14:45', '38c8c816-a879-46e2-ac81-a357daf41610'),
       ('ccb760d0-fc7d-4f6a-946b-043ea034930e', 0, NULL, '2023-02-08 15:02:14', '38c8c816-a879-46e2-ac81-a357daf41610');
UNLOCK
    TABLES;

LOCK
    TABLES `products_orders` WRITE;
INSERT INTO `products_orders`
VALUES ('11', 'ab', '11', '23b21dbc-ab15-45fa-888c-cc9a2653394b', 2, 100),
       ('21cdf7b1-c758-45f8-b4fa-b1ca33c1ff9d', 'a', '5', '79188056-38c6-4486-adf1-fe64fa69edb9', 1, 44),
       ('228992e8-19c0-4160-a387-d1755567fa87', 'a', '5', '0aeb61dc-4d15-49f3-8314-ca8b30c907ad', 1, 44),
       ('2f51a0e4-3459-4a57-ae6e-7ecb34c2aacc', 'ab', '11', 'b75fa99e-84f6-425d-985a-539f1ccf2eda', 1, 100),
       ('446cd5df-5ea8-48c0-b0af-efcc70badb60', 'd', '8', 'ccb760d0-fc7d-4f6a-946b-043ea034930e', 1, 77),
       ('5', 'a', '5', '23b21dbc-ab15-45fa-888c-cc9a2653394b', 2, 44),
       ('671bb1a7-1578-4266-bbd1-a196ae8e916a', 'ac', '12', '9a9fdf42-9761-4956-866c-77b7ee94f227', 1, 111),
       ('697c4687-6474-4dbd-83c8-24e66f1aacf6', 'a', '5', '2ca73fea-5b4b-4e46-b320-1509cbd56cb7', 2, 44),
       ('75603faf-9256-44d6-b276-ecdb5fc55ebd', 'a', '5', 'b75fa99e-84f6-425d-985a-539f1ccf2eda', 1, 44),
       ('87047502-2ebc-45cf-bc9a-cd9307c46430', 'a', '5', 'ca2a2d98-83b9-4873-a6f8-b3c25a16cc6a', 1, 44),
       ('952abc2c-c15d-4ea6-82e1-2581033545ab', 'a', '5', '8da0d514-6f98-43d9-ac39-2e93e6b7c2f6', 1, 44),
       ('9c9dc06d-ba81-4fe4-a5a7-8e5bcde3a0ab', 'a', '5', '0d9b636b-0044-4851-bbc1-58bbbca47af8', 1, 44),
       ('e7f0a839-98df-48e5-b50b-355ad10d2cb9', 'a', '5', 'c6d6e30a-95c9-40d8-85ee-2a1e58457aa2', 1, 44),
       ('ec36b4ae-3bde-42c6-8c34-a19c03585ebf', 'a', '5', '526c1b84-84bd-4cb9-93a4-871197c2711b', 1, 44);
UNLOCK
    TABLES;