DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2021-06-19 09:00', 'Завтрак user', 500),
       (100000, '2021-06-19 12:30', 'Обед user', 1100),
       (100000, '2021-06-19 18:30', 'Ужин user', 700),
       (100000, '2021-06-20 00:00', 'Еда в непонятное время user', 400),
       (100000, '2021-06-20 13:00', 'Обед user', 1000),
       (100000, '2021-06-20 18:00', 'Ужин user', 450),
       (100001, '2021-06-19 07:30', 'Завтрак admin', 600),
       (100001, '2021-06-19 12:00', 'Обед admin', 1200),
       (100001, '2021-06-19 19:00', 'Ужин admin', 400);