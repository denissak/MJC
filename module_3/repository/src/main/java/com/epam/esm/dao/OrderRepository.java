package com.epam.esm.dao;

import com.epam.esm.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends AbstractRepository<OrderEntity>{

    List<OrderEntity> readAllOrdersByUserId (long userId, int page, int size);

    void setCertificatesOnOrder(Long orderId, Long certificateId);
}
