DROP TABLE gift_certificate_m2m_tag;
DROP TABLE tag;
DROP TABLE gift_certificate;
DROP TABLE orders;
DROP TABLE order_certificate_m2m;
DROP TABLE users;

ALTER TABLE public.users ADD COLUMN name VARCHAR (50);
ALTER TABLE public.users ADD COLUMN surname VARCHAR (50);
ALTER TABLE public.users ADD COLUMN email VARCHAR (50);
ALTER TABLE public.tag ADD COLUMN image VARCHAR (50);
ALTER TABLE public.gift_certificate ADD COLUMN image VARCHAR (50);

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(40)  NOT NULL,
    password VARCHAR(255) NOT NULL,
    name     VARCHAR(50) NOT NULL,
    surname  VARCHAR(50) NOT NULL,
    email    varchar(50) NOT NULL,
    role_id  BIGINT       NOT NULL,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE NO ACTION
);

DROP TABLE orders;
CREATE TABLE orders
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(40) NOT NULL,
    cost    NUMERIC     NOT NULL,
    date    TIMESTAMP   NOT NULL,
    user_id BIGINT      NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE gift_certificate
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(40) NOT NULL,
    description      VARCHAR(150) NOT NULL,
    price            NUMERIC     NOT NULL,
    duration         INT         NOT NULL,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    image VARCHAR(50) NOT NULL
);

CREATE TABLE roles
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE gift_certificate_m2m_tag
(
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,
    FOREIGN KEY (gift_certificate_id)
        REFERENCES gift_certificate (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (tag_id)
        REFERENCES tag (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);


CREATE TABLE order_certificate_m2m
(
    id             BIGSERIAL PRIMARY KEY,
    order_id       BIGINT NOT NULL,
    certificate_id BIGINT NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES orders (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (certificate_id)
        REFERENCES gift_certificate (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

CREATE TABLE order_certificate_m2m
(
    order_id       BIGINT NOT NULL,
    certificate_id BIGINT NOT NULL,
    FOREIGN KEY (order_id)
        REFERENCES orders (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
    FOREIGN KEY (certificate_id)
        REFERENCES gift_certificate (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('Swimming pool', 'Subscription for 12 lessons in the pool.', 30, 90, now(), now()),
       ('PizzaHut', 'Fast food restaurant. We offered discount 20% on wednesday.', 10, 90, now(), now()),
       ('Carsold.com', 'Salon of used cars. We offer cars with a 5% discount', 50, 90, now(), now()),
       ('Bowling club "Strike"', 'Weekend bowling discount.', 7, 90, now(), now()),
       ('Carting club "Fast"', 'Weekend carting discount.', 6, 90, now(), now()),
       ('Nvidia', 'Video card with discount 8%.', 30, 90, now(), now()),
       ('Spa center', 'Discount for solarium from 10-12 AM', 10, 90, now(), now()),
       ('Beauty shop', 'All cosmetics from Korea with a 25% discount.', 19.99, 90, now(), now()),
       ('Event agency "Yellow elephant"', 'Discount 17%.', 10, 90, now(), now()),
       ('Electronic stock', 'Only the lowest prices. Discount on all goods 7%', 4.99, 90, now(), now()),
       ('Amazon.com', 'The black friday 30% price off.', 9.99, 90, now(), now()),
       ('Rose.com', 'Discount for flowers 15%', 3.19, 90, now(), now()),
       ('Beauty Dent', 'Annual insurance discount 10%.', 48.99, 90, now(), now()),
       ('Car Service 1', '20% discount on oil change', 5, 90, now(), now()),
       ('Library.com', 'Discount for marvel comics', 19.99, 90, now(), now()),
       ('Medical inc', 'Health insurance for year with 20% discount.', 70, 90, now(), now()),
       ('Adidas', 'New collection with discount 10%.', 15, 90, now(), now()),
       ('Apple', 'New gadgets with discount 7%.', 45, 90, now(), now()),
       ('Lego', 'Discount for all our toys 20%.', 30, 90, now(), now()),
       ('Pizza Italy', 'Italian restaurant.We offered you discount 20%.', 9.99, 90, now(), now()),
       ('Nike', 'Clothes and shoes shop. Our discount is 12% for all.', 17.99, 90, now(), now()),
       ('Beauty SPA', '30% discount for all!', 10, 90, now(), now()),
       ('Super Mario', 'Repair of water supply systems with discount 20%.', 20, 90, now(), now()),
       ('Epson', 'Discount for printers 10%.', 30, 90, now(), now()),
       ('5D park', 'Entrance tickets with 10% discount', 10.99, 90, now(), now()),
       ('Cinema', '5% discount on weekday tickets.', 1.99, 90, now(), now()),
       ('Sony', 'Electronics store, you can buy equipment with a 7% discount.', 13.99, 90, now(), now()),
       ('Discount center #1', 'Cheap clothing store. Discount for 15%.', 15, 90, now(), now());

INSERT INTO tag (name, image)
VALUES ('Food'), /*1*/
       ('Clothes'), /*2*/
       ('Flowers'), /*3*/
       ('Entertainment'), /*4*/
       ('Birthday'), /*5*/
       ('Man'), /*6*/
       ('Woman'), /*7*/
       ('Children'), /*8*/
       ('Low Price'), /*9*/
       ('Popular'), /*10*/
       ('Health'), /*11*/
       ('Books'), /*12*/
       ('Cosmetics'), /*13*/
       ('Toys'), /*14*/
       ('Electronic'), /*15*/
       ('Car'), /*16*/
       ('Repair'), /*17*/
       ('SPA'); /*18*/

INSERT INTO gift_certificate_m2m_tag (gift_certificate_id, tag_id)
VALUES (1, 4), (1, 6), (1, 7), (1, 8), (1, 10), (1, 11), (1, 18),
        (2, 1), (2, 4), (2, 10),
        (3, 6), (3, 7), (3, 16),
        (4, 4), (4, 5), (4, 10),
        (5, 4), (5, 5), (5, 6),
        (6, 4), (6, 8), (6, 15),
        (7, 7), (7, 11), (7, 18),
        (8, 7), (8, 9), (8, 10), (8, 11), (8, 13),
        (9, 4), (9, 5), (9, 6), (9, 7), (9, 8), (9, 10),
        (10, 6), (10, 8), (10, 9), (10, 10), (10, 15),
        (11, 2), (11, 6), (11, 7), (11, 8), (11, 9), (11, 10), (11, 12), (11, 13), (11, 14), (11, 15),
        (12, 3), (12, 5), (12, 7),
        (13, 6), (13, 7), (13, 8), (13, 11),
        (14, 6), (14, 9), (14, 10), (14, 16), (14, 17),
        (15, 6), (15, 7), (15, 8), (15, 12),
        (16, 6), (16, 7), (16, 11),
        (17, 2), (17, 6), (17, 7), (17, 8), (17, 10),
        (18, 6), (18, 7), (18, 10), (18, 15),
        (19, 6), (19, 8), (19, 10), (19, 14),
        (20, 1), (20, 5), (20, 6), (20, 7),
        (21, 2), (21, 6), (21, 7), (21, 8), (21, 10),
        (22, 7), (22, 11), (22, 18),
        (23, 9), (23, 17),
        (24, 15),
        (25, 4), (25, 6), (25, 7), (25, 8),
        (26, 4), (26, 6), (26, 7), (26, 8), (26, 10),
        (27, 6), (26, 7), (26, 8), (26, 10), (26, 15),
        (28, 2), (28, 6), (28, 7), (28, 8), (28, 9), (28, 10);

INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('USER'),
       ('GUEST');

INSERT INTO orders (name, cost, date, user_id)
VALUES ('order1', 100, now(), 1),
       ('order2', 200, now(), 2),
       ('order3', 300, now(), 3),
       ('order4', 400, now(), 4);

INSERT INTO users (login, password, name, surname, email, role_id)
VALUES ('user1', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 'Admin', 'Admin', 'admin@gmail.com', 1),
       ('user2', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 'User', 'User', 'user@gmail.com', 2),
       ('user3', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 'User2', 'User2', 'user2@gmail.com', 3),
       ('user4', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 'User3', 'User3', 'user3@gmail.com', 2);

INSERT INTO order_certificate_m2m (order_id, certificate_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1);
