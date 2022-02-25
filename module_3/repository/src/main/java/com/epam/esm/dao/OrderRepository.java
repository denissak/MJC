package com.epam.esm.dao;

import com.epam.esm.entity.OrderEntity;

import java.util.List;

/**
 * Contains methods for working mostly with {@code Order} entity.
 */
public interface OrderRepository extends AbstractRepository<OrderEntity> {

    /**
     * Reads all orders by user.
     *
     * @return OrderEntities which meet passed parameters
     */
    List<OrderEntity> readAllOrdersByUserId(long userId, int page, int size);

    /**
     * Attach certificate to the order
     *
     * @param orderId         tag id which needs to set in m2m table
     * @param certificateId certificate id which need to set in m2m table
     */
    void setCertificatesOnOrder(Long orderId, Long certificateId);
}
