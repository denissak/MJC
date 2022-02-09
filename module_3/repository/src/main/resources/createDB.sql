DROP TABLE gift_certificate_m2m_tag;
DROP TABLE tag;
DROP TABLE gift_certificate;



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

CREATE TABLE tagEntity
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(40) NOT NULL
);

CREATE TABLE gift_certificate_m2m_tag
(
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,
    PRIMARY KEY (gift_certificate_id, tag_id),
        FOREIGN KEY (gift_certificate_id)
        REFERENCES gift_certificate (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION,
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION)
;

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