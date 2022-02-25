package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Contains methods implementation for working mostly with {@code User}
 * entity.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final PaginationHandlerImpl paginationHandler;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(PaginationHandlerImpl paginationHandler) {
        this.paginationHandler = paginationHandler;
    }

    /**
     * Reads user with passed id.
     *
     * @param userId the id of entity to be read
     * @return entity with passed id
     */
    @Override
    public UserEntity readById(long userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot = criteria.from(UserEntity.class);
        criteria.select(userEntityRoot)
                .where(cb.equal(userEntityRoot.get("id"), userId));
        List<UserEntity> userEntityList = entityManager.createQuery(criteria).getResultList();
        if (userEntityList.size() > 0) {
            return userEntityList.get(0);
        } else
            return null;
    }

    /**
     * Reads all users according to passed parameters.
     *
     * @return entities which meet passed parameters
     */
    @Override
    public List<UserEntity> readAll(int page, int size) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot = criteria.from(UserEntity.class);
        CriteriaQuery<UserEntity> select = criteria.select(userEntityRoot);
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        countQuery.select(cb.count(countQuery.from(UserEntity.class)));
        TypedQuery<UserEntity> typedQuery = entityManager.createQuery(select);
        paginationHandler.setPageToQuery(typedQuery, page, size);
        return typedQuery.getResultList();
    }
}
