package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagRepository;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.mapper.TagRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

/**
 * Contains methods implementation for working mostly with {@code Tag}
 * entity.
 */
@Repository
public class TagRepositoryImpl implements TagRepository {

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GET_TAG_BY_NAME = "SELECT * FROM tag WHERE name = ?";
    private static final String GET_TAG_BY_ID = "SELECT * FROM tag WHERE id = %s";
    private static final String GET_ALL_TAGS = "SELECT * FROM tag";
    private static final String SAVE_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    private JdbcTemplate jdbcTemplate;
    private TagRowMapper tagRowMapper;


    @Autowired
    public TagRepositoryImpl(JdbcTemplate jdbcTemplate, TagRowMapper tagRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagRowMapper = tagRowMapper;
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
    public Optional<TagEntity> readById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity)
                .where(cb.equal(tagEntity.get("id"), id));
        return Optional.ofNullable(entityManager.createQuery(criteria).getResultList().get(0));
    }

    /**
     * Reads tag with passed name.
     *
     * @param name the name of entity to be read
     * @return tag with passed name
     */
    @Override
    public Optional<TagEntity> readByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity)
                .where(cb.equal(tagEntity.get("name"), name));
        return Optional.ofNullable(entityManager.createQuery(criteria).getResultList().get(0));
    }

    /**
     * Reads all tagEntities.
     *
     * @return all tagEntities
     */
    @Override
    public List<TagEntity> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TagEntity> criteria = cb.createQuery(TagEntity.class);
        Root<TagEntity> tagEntity = criteria.from(TagEntity.class);
        criteria.select(tagEntity);
        return entityManager.createQuery(criteria).getResultList();
    }

    /**
     * Deletes tag with passed id.
     *
     * @param id the id of tag to be deleted
     * @return the number of deleted tagEntities
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
