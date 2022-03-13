package com.epam.esm.dao;

import com.epam.esm.entity.TagEntity;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.annotations.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Contains methods for working mostly with {@code Tag} entity.
 */

public interface TagRepository extends JpaRepository<TagEntity, Long> {

        String MOST_POPULAR_TAG = "SELECT tag.id, tag.name\n" +
            "FROM tag\n" +
            "         JOIN gift_certificate_m2m_tag gcm2mt on tag.id = gcm2mt.tag_id\n" +
            "         JOIN gift_certificate gc on gcm2mt.gift_certificate_id = gc.id\n" +
            "         JOIN order_certificate_m2m ocm2m on gc.id = ocm2m.certificate_id\n" +
            "         JOIN orders as o on ocm2m.order_id = o.id\n" +
            "WHERE o.user_id = (\n" +
            "    select user_id from orders group by user_id having sum(cost) >= ALL (select sum(cost) from orders group by user_id))\n" +
            "GROUP BY tag.id, tag.name\n" +
            "ORDER BY count(tag.name) DESC\n" +
            "LIMIT 1";

    TagEntity readByName (String name);

    /**
     * Read most popular tag by max cost order.
     *
     * @return TagEntity which meet passed parameters
     */
    @Query(value = MOST_POPULAR_TAG,
            nativeQuery = true)
    TagEntity getMostPopularTag();

}
