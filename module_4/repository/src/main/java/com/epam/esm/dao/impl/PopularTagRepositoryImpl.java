package com.epam.esm.dao.impl;

import com.epam.esm.dao.PopularTagRepository;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class PopularTagRepositoryImpl implements PopularTagRepository {

    private static final String MOST_POPULAR_TAG = "SELECT tag.id, tag.name\n" +
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

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public PopularTagRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Read most popular tag by max cost order.
     *
     * @return TagEntity which meet passed parameters
     */

    @Override
    public TagEntity getMostPopularTag() {
        return (TagEntity) entityManager.createNativeQuery(MOST_POPULAR_TAG, TagEntity.class).getSingleResult();
    }
}
