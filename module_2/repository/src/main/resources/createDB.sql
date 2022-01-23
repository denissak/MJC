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

CREATE TABLE gift_certificate_m2m_tag
(
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,
    PRIMARY KEY (gift_certificate_id, tag_id)
);

INSERT INTO gift_certificate (id, name, description, price, duration, create_date, last_update_date)
VALUES (1, 'cert1', 'nice', 5, 90, now(), now()),
       (2, 'cert2', 'bad', 7, 90, now(), now());

INSERT INTO tag (id, name)
VALUES (1, 'tag1'),
       (2, 'tag2'),
       (3, 'tag3'),
       (4, 'tag4');

INSERT INTO gift_certificate_m2m_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1);