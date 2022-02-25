CREATE TABLE gift_certificate
(
    id               BIGINT         NOT NULL AUTO_INCREMENT,
    name             VARCHAR(40) NULL,
    description      VARCHAR(50) NULL,
    price            DECIMAL     NOT NULL,
    duration         INTEGER     NOT NULL,
    create_date      TIMESTAMP   NULL,
    last_update_date TIMESTAMP   NULL,
    PRIMARY KEY (id)
);


CREATE TABLE tagEntity
(
    id   BIGINT         NOT NULL AUTO_INCREMENT,
    name VARCHAR(40) NULL,
    PRIMARY KEY (id)
);

CREATE TABLE gift_certificate_m2m_tag
(
    tag_id              BIGINT NOT NULL,
    gift_certificate_id BIGINT NOT NULL,
    CONSTRAINT certificate_fk
        FOREIGN KEY (gift_certificate_id)
            REFERENCES gift_certificate (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION,
    CONSTRAINT tag_fk
        FOREIGN KEY (tag_id)
            REFERENCES tag (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);