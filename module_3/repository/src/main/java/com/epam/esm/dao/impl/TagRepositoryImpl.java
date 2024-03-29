package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Contains methods implementation for working mostly with {@code Tag}
 * entity.
 */
@Repository
public class TagRepositoryImpl implements TagRepository {

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

    private final PaginationHandlerImpl paginationHandler;

    @Autowired
    public TagRepositoryImpl(PaginationHandlerImpl paginationHandler) {
        this.paginationHandler = paginationHandler;
    }

    /**
     * Saves the passed tag.
     *
     * @param tagEntity the tag to be saved
     * @return saved tag
     */
    @Override
    @Transactional
    public TagEntity create(TagEntity tagEntity) {
        entityManager.persist(tagEntity);
        return tagEntity;
    }

    /**
     * Reads tag with passed id.
     *
     * @param id the id of tag to be read
     * @return tag with passed id
     */
    @Override
    public TagEntity readById(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity)
                .where(criteriaBuilder.equal(tagEntity.get("id"), id));
        List<TagEntity> entityList = entityManager.createQuery(criteria).getResultList();
        if (entityList.size() > 0) {
            return entityList.get(0);
        } else
            return null;

    }

    /**
     * Reads tag with passed name.
     *
     * @param name the name of entity to be read
     * @return tag with passed name
     */
    @Override
    public TagEntity readByName(String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity)
                .where(criteriaBuilder.equal(tagEntity.get("name"), name));
        List<TagEntity> resultList = entityManager.createQuery(criteria).getResultList();
        if (resultList.size() > 0) {
            return resultList.get(0);
        } else
            return null;
    }

    /**
     * Reads all tagEntities.
     *
     * @param page numbers of page
     * @param size number of elements per page
     *
     * @return all tagEntities
     */
    @Override
    public List<TagEntity> readAll(int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        CriteriaQuery<TagEntity> select = criteria.select(tagEntity);
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(TagEntity.class)));
        TypedQuery<TagEntity> typedQuery = entityManager.createQuery(select);
        paginationHandler.setPageToQuery(typedQuery, page, size);
        return typedQuery.getResultList();
    }

    @Override
    public TagEntity getMostPopularTag() {
        return (TagEntity) entityManager.createNativeQuery(MOST_POPULAR_TAG, TagEntity.class).getSingleResult();
    }

    /**
     * Deletes tag with passed id.
     *
     * @param id the id of tag to be deleted
     */
    @Override
    public void delete(Long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = criteriaBuilder.createQuery(TagEntity.class);
        Root<TagEntity> tagEntityRoot = criteria.from(TagEntity.class);
        criteria.select(tagEntityRoot)
                .where(criteriaBuilder.equal(tagEntityRoot.get("id"), id));
        TagEntity tagEntity = entityManager.createQuery(criteria).getResultList().get(0);
        entityManager.remove(tagEntity);
    }
}
