package com.epam.esm;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;

import java.util.List;

/**
 * Contains methods for working mostly with {@code OrderDto} entity.
 */
public interface OrderService {

    /**
     * Create and save the passed order.
     *
     * @param orderDto the order to be saved
     * @return saved order
     */
//    OrderDto create(OrderDto orderDto);

    OrderDto create(List<Long> certificateIds, long userId);

    /**
     * Read order with passed id.
     *
     * @param orderId the id of order to be read
     * @return order with passed id
     */
    OrderDto readById(Long orderId);

    /**
     * Reads all orders.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all orders
     */
    List<OrderDto> readAll(int page, int size);

    /**
     * Delete order with passed id.
     *
     * @param orderId the id of order to be deleted
     */
    void delete(Long orderId);

    /**
     * Read all orders by user.
     *
     * @param userId the id of user to be sorted
     * @param page numbers of page
     * @param size number of elements per page
     * @return orderDto which meet passed parameters
     */
    List<OrderDto> readAllOrdersByUserId(long userId, int page, int size);

    /**
     * Read cost and date orders by user.
     *
     * @param userId the id of user to be sorted
     * @param page numbers of page
     * @param size number of elements per page
     * @return readOrderDto which meet passed parameters
     */
    List<ReadOrderDto> readCostAndDateOrderByUserId(long userId, int page, int size);
}
