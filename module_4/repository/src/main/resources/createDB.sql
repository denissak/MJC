DROP TABLE gift_certificate_m2m_tag;
DROP TABLE tag;
DROP TABLE gift_certificate;
DROP TABLE orders;
DROP TABLE order_certificate_m2m;
DROP TABLE users;

CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    login    VARCHAR(40)  NOT NULL,
    password VARCHAR(255) NOT NULL,
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
    description      VARCHAR(50) NOT NULL,
    price            NUMERIC     NOT NULL,
    duration         INT         NOT NULL,
    create_date      TIMESTAMP,
    last_update_date TIMESTAMP
);

CREATE TABLE tag
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL
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

INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('cert1', 'nice', 5, 90, now(), now()),
       ('cert2', 'bad', 7, 90, now(), now()),
       ('cert3', 'bad', 8, 90, now(), now());

INSERT INTO tag (name)
VALUES ('tag1'),
       ('tag2'),
       ('tag3'),
       ('tag4');

INSERT INTO gift_certificate_m2m_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1);

INSERT INTO roles (name)
VALUES ('ADMIN'),
       ('USER'),
       ('GUEST');

INSERT INTO orders (name, cost, date, user_id)
VALUES ('order1', 100, now(), 1),
       ('order2', 200, now(), 2),
       ('order3', 300, now(), 3),
       ('order4', 400, now(), 4);

INSERT INTO users (login, password, role_id)
VALUES ('user1', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 1),
       ('user2', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 2),
       ('user3', '$2a$12$LlyVzVpBlXZT7aOrVClygeA/2R6Sa3QnBdMPu2cOsU62clvcXscbO', 3),
       ('user4', '123', 2);

INSERT INTO order_certificate_m2m (order_id, certificate_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1);
