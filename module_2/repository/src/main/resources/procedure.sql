create or replace function procedure(tagValue varchar(45), query varchar(45),
                             sortBy varchar(20), ascending boolean)
                             returns procedure()
    LANGUAGE 'plpgsql'
as $$

begin

    BEGIN
/*        IF (tagValue IS NOT NULL AND query IS NOT NULL ) THEN*/
            SELECT * from gift_certificate gc
                              JOIN gift_certificate_m2m_tag m2m on gc.id = m2m.gift_certificate_id
                              JOIN tag on m2m.tag_id = tag.id
            WHERE tag.name = tagValue AND (gc.name LIKE CONCAT('%', query , '%')
                OR gc.description LIKE CONCAT('%', query , '%'))
            ORDER BY
                CASE WHEN sortBy= 'name' AND ascending IS FALSE THEN gc.name END DESC,
                CASE WHEN sortBy= 'name' AND ascending IS TRUE THEN gc.name END,
                CASE WHEN sortBy= 'date' AND ascending IS TRUE THEN gc.last_update_date END,
                CASE WHEN sortBy= 'date' AND ascending IS FALSE THEN gc.last_update_date END DESC;
       /* ELSEIF (tagValue IS NOT NULL) THEN
            SELECT * from gift_certificate gc
                              JOIN gift_certificate_m2m_tag m2m on gc.id = m2m.gift_certificate_id
                              JOIN tag on m2m.tag_id = tag.id
            WHERE tag.name = tagValue
            ORDER BY
                CASE WHEN sortBy='name' AND ascending IS FALSE THEN gc.name END DESC,
                CASE WHEN sortBy='name' AND ascending IS TRUE THEN gc.name END,
                CASE WHEN sortBy='date' AND ascending IS TRUE THEN gc.last_update_date END,
                CASE WHEN sortBy='date' AND ascending IS FALSE THEN gc.last_update_date END DESC;
        ELSEIF (query IS NOT NULL) THEN
            SELECT * from gift_certificate gc
                              JOIN gift_certificate_m2m_tag m2m on gc.id = m2m.gift_certificate_id
                              JOIN tag on m2m.tag_id = tag.id
            WHERE gc.name LIKE CONCAT('%', query , '%')
               OR gc.description LIKE CONCAT('%', query , '%')
            ORDER BY
                CASE WHEN sortBy= 'name' AND ascending IS FALSE THEN gc.name END DESC,
                CASE WHEN sortBy= 'name' AND ascending IS TRUE THEN gc.name END,
                CASE WHEN sortBy= 'date' AND ascending IS TRUE THEN gc.last_update_date END,
                CASE WHEN sortBy= 'date' AND ascending IS FALSE THEN gc.last_update_date END DESC;
        end if;*/
    END;

end;
$$