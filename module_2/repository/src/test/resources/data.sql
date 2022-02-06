INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date)
VALUES ('cert1', 'nice', 5, 90, '2022-02-06T00:39:00', '2022-02-06T00:39:00'),
       ('cert2', 'bad', 7, 90, '2022-02-06T00:39:00','2022-02-06T00:39:00'),
       ('cert3', 'bad', 8, 90, '2022-02-06T00:39:00', '2022-02-06T00:39:00');

INSERT INTO tag (name)
VALUES ('tag1'),
       ('tag2'),
       ('tag3');

INSERT INTO gift_certificate_m2m_tag (gift_certificate_id, tag_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 1);