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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity)
                .where(cb.equal(tagEntity.get("id"), id));
        List<TagEntity> entityList = entityManager.createQuery(criteria).getResultList();
        if (entityList.size() > 0){
            return entityList.get(0);
        }else
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity)
                .where(cb.equal(tagEntity.get("name"), name));
        List<TagEntity> resultList = entityManager.createQuery(criteria).getResultList();
        if (resultList.size() > 0){
            return resultList.get(0);
        }else
        return null;
    }

    /**
     * Reads all tagEntities.
     *
     * @return all tagEntities
     */
    @Override
    public List<TagEntity> readAll(int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        CriteriaQuery<TagEntity> select = criteria.select(tagEntity);
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(TagEntity.class)));
        TypedQuery<TagEntity> typedQuery = entityManager.createQuery(select);
        paginationHandler.setPageToQuery(typedQuery, page, size);
        return typedQuery.getResultList();
    }

    /**
     * Deletes tag with passed id.
     *
     * @param id the id of tag to be deleted
     */
    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntityRoot = criteria.from(TagEntity.class);
        criteria.select(tagEntityRoot)
                .where(cb.equal(tagEntityRoot.get("id"), id));
        TagEntity tagEntity = entityManager.createQuery(criteria).getResultList().get(0);
        entityManager.remove(tagEntity);
    }
}
