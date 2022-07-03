INSERT INTO USERS (NAME, LOGIN, PASSWORD)
VALUES ('User', 'user', '{noop}user'),
       ('Admin', 'admin', '{noop}admin');

INSERT INTO USER_ROLES (USER_ID, ROLE)
VALUES (1, 'USER'),
       (2, 'USER'),
       (2, 'ADMIN');

INSERT INTO RESTAURANTS (NAME)
VALUES ('restaurant1'),
       ('restaurant2'),
       ('restaurant3'),
       ('restaurant4');

INSERT INTO MENUS (RESTAURANT_ID, DATE)
VALUES (1, '2022-06-16'),
       (2, '2022-06-15'),
       (3, CURRENT_DATE);

INSERT INTO DISHES (NAME, PRICE_IN_CENTS, MENU_ID)
VALUES ('dish1', 485, 1),
       ('dish2', 780, 1),
       ('dish3', 880, 1),
       ('dish4', 740, 2),
       ('dish5', 340, 2),
       ('dish6', 660, 2),
       ('dish7', 760, 2),
       ('dish8', 530, 3),
       ('dish9', 675, 3),
       ('dish10', 330, 3),
       ('dish11', 545, 3);

INSERT INTO VOTES (RESTAURANT_ID, USER_ID)
VALUES (2, 1),
       (3, 2);