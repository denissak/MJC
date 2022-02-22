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

    private static final String ORDER_SAVE = "INSERT INTO orders (name, cost, date, user_id) VALUES (?,?,?,?)";
    private static final String ORDER_CERTIFICATE_SAVE = "INSERT INTO order_certificate_m2m (order_id, certificate_id) VALUES (?,?)";
    private static final String VALUES = ", (?,?)";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public OrderEntity create(OrderEntity orderEntity) {
//        String query = String.format(ORDER_SAVE, model.getName(), 66, model.getDate(), model.getUserEntity().getId());
        /*List<OrderEntity> orderEntityList = *//*entityManager.createNativeQuery(ORDER_SAVE)
                .setParameter(1, model.getName())
                .setParameter(2, model.getCost())
                .setParameter(3, model.getDate())
                .setParameter(4, model.getUserEntity().getId()).executeUpdate();*/
        entityManager.persist(orderEntity);
        return orderEntity;
    }

    @Override
    public void setCertificatesOnOrder(Long orderId, Long certificateId) {
        /*StringBuilder query = new StringBuilder();
        query.append(ORDER_CERTIFICATE_SAVE);
        int certificateSize = orderEntity.getOrderCertificateEntityList().size();
        if (certificateSize > 1) {
            for (int i = 1; i > certificateSize; i++) {
                query.append(VALUES);
            }
        }
        String.format(TAG_CONDITION, i, i, i, i, i)*/
        entityManager.createNativeQuery(ORDER_CERTIFICATE_SAVE)
                .setParameter(1, orderId)
                .setParameter(2, certificateId)/*.executeUpdate()*/;
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
                .where(cb.equal(orderEntityRoot.get("name"), name));
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
        criteria.select(orderEntityRoot).where(cb.equal(users.get(UserEntity_.id), userId));
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
