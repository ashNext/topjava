DELETE
FROM meals;

DELETE
FROM user_roles;

DELETE
FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-06-20 07:40:00', 'Завтрак', 500),
       (100000, '2020-06-20 13:00:00', 'Обед', 1000),
       (100000, '2020-06-20 19:00:00', 'Ужин', 500),
       (100000, '2020-06-21 07:40:00', 'Завтрак', 300),
       (100000, '2020-06-21 12:00:00', 'Ланч', 100),
       (100000, '2020-06-21 14:00:00', 'Обед', 1000),
       (100000, '2020-06-21 19:30:00', 'Ужин', 700),
       (100000, '2020-06-22 08:40:00', 'Завтрак', 700),
       (100000, '2020-06-22 14:00:00', 'Обед', 700),
       (100000, '2020-06-22 20:00:00', 'Ужин', 500),
       (100001, '2020-06-20 07:40:00', 'Завтрак', 600),
       (100001, '2020-06-20 12:00:00', 'Ланч', 100),
       (100001, '2020-06-20 13:00:00', 'Обед', 700),
       (100001, '2020-06-20 19:00:00', 'Ужин', 700),
       (100001, '2020-06-21 07:40:00', 'Завтрак', 900),
       (100001, '2020-06-21 14:00:00', 'Обед', 800),
       (100001, '2020-06-21 19:30:00', 'Ужин', 200),
       (100001, '2020-06-22 08:40:00', 'Завтрак', 700),
       (100001, '2020-06-22 12:00:00', 'Ланч', 100),
       (100001, '2020-06-22 14:00:00', 'Обед', 900),
       (100001, '2020-06-22 20:00:00', 'Ужин', 400);