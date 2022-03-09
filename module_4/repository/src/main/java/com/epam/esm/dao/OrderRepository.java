package com.epam.esm.dao;

import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Contains methods for working mostly with {@code Order} entity.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    OrderEntity findByName(String orderName);

    List<OrderEntity> findAllById(Long orderId, Pageable pageable);



//    /**
//     * Reads all orders by user.
//     *
//     * @return OrderEntities which meet passed parameters
//     */
//    List<OrderEntity> readAllOrdersByUserId(long userId, int page, int size);

//    /**
//     * Attach certificate to the order
//     *
//     * @param orderId         tag id which needs to set in m2m table
//     * @param certificateId certificate id which need to set in m2m table
//     */
//    void setCertificatesOnOrder(Long orderId, Long certificateId);

}
