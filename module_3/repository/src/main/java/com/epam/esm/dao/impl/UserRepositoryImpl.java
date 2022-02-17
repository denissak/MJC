package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserRepository;
import com.epam.esm.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

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

    @Override
    public UserEntity readByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot = criteria.from(UserEntity.class);
        criteria.select(userEntityRoot)
                .where(cb.equal(userEntityRoot.get("login"), login));
        List<UserEntity> userEntityList = entityManager.createQuery(criteria).getResultList();
        if (userEntityList.size() > 0) {
            return userEntityList.get(0);
        } else
            return null;
    }

    @Override
    public List<UserEntity> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<UserEntity> criteria = cb.createQuery(UserEntity.class);
        Root<UserEntity> userEntityRoot = criteria.from(UserEntity.class);
        criteria.select(userEntityRoot);
        return entityManager.createQuery(criteria).getResultList();
    }
}
