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

INSERT INTO MENUS (RESTAURANT_ID, DATE, DISHES)
VALUES (1, '2023-09-09', 'dish1	485		dish2	780		dish3	880		dish4	740'),
       (1, '2023-09-08', 'dish5	340		dish6	660		dish7	760		dish8	530'),
       (1, CURRENT_DATE, 'dish9	675		dish10	330		dish11	545'),
       (2, '2023-09-08', 'dish1	485		dish2	780		dish3	880		dish4	740'),
       (2, CURRENT_DATE, 'dish5	340		dish6	660		dish7	760'),
       (3, '2023-09-08', 'dish1	485		dish2	780		dish3	880'),
       (3, CURRENT_DATE, 'dish4	740		dish5	340		dish6	660		dish7	760');


INSERT INTO VOTES (RESTAURANT_ID, USER_ID)
VALUES (2, 1),
       (3, 2);