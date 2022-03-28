package com.epam.esm.dao;

import com.epam.esm.entity.OrderEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Contains methods for working mostly with {@code Order} entity.
 */
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {

    /**
     * Reads OrderEntity with passed name.
     *
     * @param orderName the name of entity to be read
     * @return entity with passed name
     */
    OrderEntity findByName(String orderName);

    /**
     * Reads OrderEntity with passed id.
     *
     * @param userId the user with passed
     * @param pageable pagination options
     * @return orders with passed user id
     */
    List<OrderEntity> findAllById(Long userId, Pageable pageable);
}
