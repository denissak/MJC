//package com.epam.esm.dao.impl;
//
//import com.epam.esm.dao.OrderRepository;
//import com.epam.esm.entity.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Join;
//import javax.persistence.criteria.Root;
//import java.util.List;
//
///**
// * Contains methods for working mostly with {@code Order} entity.
// */
//@Repository
//public class OrderRepositoryImpl implements OrderRepository {
//
//    private static final String ORDER_CERTIFICATE_SAVE = "INSERT INTO order_certificate_m2m (order_id, certificate_id) VALUES (?,?)";
//    private final PaginationHandlerImpl paginationHandler;
//
//    @Autowired
//    public OrderRepositoryImpl(PaginationHandlerImpl paginationHandler) {
//        this.paginationHandler = paginationHandler;
//    }
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    /**
//     * Saves the passed order.
//     *
//     * @param orderEntity the order to be saved
//     * @return saved orderEntity
//     */
//    @Override
//    public OrderEntity create(OrderEntity orderEntity) {
//        entityManager.persist(orderEntity);
//        return orderEntity;
//    }
//
//    /**
//     * Attach certificate to the order
//     *
//     * @param orderId         tag id which needs to set in m2m table
//     * @param certificateId certificate id which need to set in m2m table
//     */
//    @Override
//    public void setCertificatesOnOrder(Long orderId, Long certificateId) {
//        entityManager.createNativeQuery(ORDER_CERTIFICATE_SAVE)
//                .setParameter(1, orderId)
//                .setParameter(2, certificateId).executeUpdate();
//    }
//
//    /**
//     * Reads order with passed id.
//     *
//     * @param id the id of order to be read
//     * @return order with passed id
//     */
//    @Override
//    public OrderEntity readById(Long id) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
//        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
//        criteria.select(orderEntityRoot)
//                .where(cb.equal(orderEntityRoot.get("id"), id));
//        List<OrderEntity> orderEntityList = entityManager.createQuery(criteria).getResultList();
//        if (orderEntityList.size() > 0) {
//            return orderEntityList.get(0);
//        } else
//            return null;
//    }
//
//    /**
//     * Reads order with passed name.
//     *
//     * @param name the id of order to be read
//     * @return order with passed name
//     */
//    @Override
//    public OrderEntity readByName(String name) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
//        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
//        criteria.select(orderEntityRoot)
//                .where(cb.equal(orderEntityRoot.get("name"), name));
//        List<OrderEntity> orderEntityList = entityManager.createQuery(criteria).getResultList();
//        if (orderEntityList.size() > 0) {
//            return orderEntityList.get(0);
//        } else
//            return null;
//    }
//
//    /**
//     * Reads all orders.
//     *
//     * @return all orders
//     */
//    @Override
//    public List<OrderEntity> readAll(int page, int size) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
//        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
//        CriteriaQuery<OrderEntity> select = criteria.select(orderEntityRoot);
//        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//        countQuery.select(cb.count(countQuery.from(OrderEntity.class)));
//        TypedQuery<OrderEntity> typedQuery = entityManager.createQuery(select);
//        paginationHandler.setPageToQuery(typedQuery, page, size);
//        return typedQuery.getResultList();
//    }
//
//    /**
//     * Reads all orders by user.
//     *
//     * @return OrderEntities which meet passed parameters
//     */
//    @Override
//    public List<OrderEntity> readAllOrdersByUserId(long userId, int page, int size) { //TODO NEED TEST
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
//        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
//        criteria.select(orderEntityRoot);
//        Join<OrderEntity, UserEntity> users = orderEntityRoot.join(OrderEntity_.userEntity);
//        CriteriaQuery<OrderEntity> select = criteria.select(orderEntityRoot).where(cb.equal(users.get(UserEntity_.id), userId));
//        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
//        countQuery.select(cb.count(countQuery.from(OrderEntity.class)));
//        TypedQuery<OrderEntity> typedQuery = entityManager.createQuery(select);
//        paginationHandler.setPageToQuery(typedQuery, page, size);
//        return typedQuery.getResultList();
//    }
//
//    /**
//     * Deletes certificate with passed id.
//     *
//     * @param id the id of entity to be deleted
//     */
//    @Override
//    public void delete(Long id) {
//        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//        CriteriaQuery<OrderEntity> criteria = cb.createQuery(OrderEntity.class);
//        Root<OrderEntity> orderEntityRoot = criteria.from(OrderEntity.class);
//        criteria.select(orderEntityRoot)
//                .where(cb.equal(orderEntityRoot.get("id"), id));
//        OrderEntity orderEntity = entityManager.createQuery(criteria).getSingleResult();
//        entityManager.remove(orderEntity);
//    }
//}
