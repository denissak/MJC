package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.entity.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderEntity create(OrderEntity model) {
        entityManager.persist(model);
        return model;
    }

    @Override
    public OrderEntity readById(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
        criteria.select(orderEntityRoot)
                .where(cb.equal(orderEntityRoot.get("id"), id));
        List<OrderEntity> orderEntityList = entityManager.createQuery(criteria).getResultList();
        if (orderEntityList.size() > 0) {
            return orderEntityList.get(0);
        } else
            return null;
    }

    @Override
    public OrderEntity readByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
        criteria.select(orderEntityRoot)
                .where(cb.equal(orderEntityRoot.get("login"), name));
        List<OrderEntity> orderEntityList = entityManager.createQuery(criteria).getResultList();
        if (orderEntityList.size() > 0) {
            return orderEntityList.get(0);
        } else
            return null;
    }

    @Override
    public List<OrderEntity> readAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
        criteria.select(orderEntityRoot);
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public List<OrderEntity> readAllOrdersByUserId(long userId) { //TODO NEED TEST
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
        criteria.select(orderEntityRoot);
        Join<OrderEntity, UserEntity> users = orderEntityRoot.join(OrderEntity_.userEntity);
        criteria.select(orderEntityRoot).where(cb.equal(users.get(UserEntity_.id),userId));
        return entityManager.createQuery(criteria).getResultList();
    }

    @Override
    public void delete(Long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
        criteria.select(orderEntityRoot)
                .where(cb.equal(orderEntityRoot.get("id"), id));
        OrderEntity orderEntity = entityManager.createQuery(criteria).getResultList().get(0);
        entityManager.remove(orderEntity);
    }
}
