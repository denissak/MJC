package com.epam.esm;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;

import java.util.List;

/**
 * Contains methods for working mostly with {@code OrderDto} entity.
 */
public interface OrderService {

    /**
     * Creates and saves the passed order.
     *
     * @param orderDto the order to be saved
     * @return saved order
     */
    OrderDto create(OrderDto orderDto);

    /**
     * Reads order with passed id.
     *
     * @param orderId the id of order to be read
     * @return order with passed id
     */
    OrderDto readById(Long orderId);

    /**
     * Reads all orders.
     *
     * @return all orders
     */
    List<OrderDto> readAll(int page, int size);

    /**
     * Deletes orders with passed id.
     *
     * @param orderId the id of order to be deleted
     */
    void delete(Long orderId);

    /**
     * Reads all orders by user.
     *
     * @param userId the id of user to be sorted
     *
     * @return orderDto which meet passed parameters
     */
    List<OrderDto> readAllOrdersByUserId (long userId, int page, int size);

    /**
     * Reads cost and date orders by user.
     *
     * @param userId the id of user to be sorted
     *
     * @return readOrderDto which meet passed parameters
     */
    List<ReadOrderDto> readCostAndDateOrderByUserId(long userId, int page, int size);
}
