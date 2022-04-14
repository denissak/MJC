package com.epam.esm.controller;

import com.epam.esm.OrderService;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Controller for working with orders.
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Read all orders.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all orders
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> readsAllOrders(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        List<OrderDto> orderDtoList = orderService.readAll(page, size);
        return addLinksToOrder(orderDtoList);
    }

    /**
     * Read order with passed id.
     *
     * @param id the id of order to be read
     * @return order with passed id
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readOrderById(@PathVariable long id) {
        try {
            Link link = linkTo(OrderController.class).withSelfRel();
            OrderDto orderDto = orderService.readById(id);
            orderDto.add(link);
            return orderDto;
        } catch (RuntimeException e){
            throw NotFoundException.notFoundWithOrderId(id).get();
        }
    }

    /**
     * Read orders with passed user id.
     *
     * @param id the id of order to be read
     * @param page numbers of page
     * @param size number of elements per page
     * @return order with passed id
     */
    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> readOrderByUserId(@PathVariable long id,
                                                       @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                       @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        try {
            List<OrderDto> orderDtoList = orderService.readAllOrdersByUserId(id, page, size);
            return addLinksToOrder(orderDtoList);
        } catch (RuntimeException e){
            throw NotFoundException.notFoundOrderWithUserId(id).get();
        }
    }

    /**
     * Read cost and date orders with passed user id.
     *
     * @param id the id of order to be read
     * @param page numbers of page
     * @param size number of elements per page
     * @return order with passed id
     */
    @GetMapping("user/{id}/status-order")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadOrderDto> readCostAndDateByUserId(@PathVariable long id,
                                                                 @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                 @RequestParam(value = "size", defaultValue = "5", required = false) int size) {
        try {
            List<ReadOrderDto> readOrderDtoList = orderService.readCostAndDateOrderByUserId(id, page, size);
            for (final ReadOrderDto readOrderDto : readOrderDtoList) {
                Link selfLink = linkTo(methodOn(OrderController.class).readOrderById(readOrderDto.getId())).withSelfRel();
                readOrderDto.add(selfLink);
            }
            Link link = linkTo(OrderController.class).withSelfRel();
            return CollectionModel.of(readOrderDtoList, link);
        } catch (RuntimeException e){
            throw NotFoundException.notFoundOrderWithUserId(id).get();
        }
    }

    /**
     * Create and save the passed order.
     *
     * @param orderDto the order to be saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody OrderDto orderDto) {
        try {
            return orderService.create(orderDto);
        } catch (RuntimeException e){
            throw DuplicateException.orderExists().get();
        }
    }

    /**
     * Delete order with passed id.
     *
     * @param id the id of order to be deleted
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable long id) {
        try {
            orderService.delete(id);
        } catch (RuntimeException e){
            throw NotFoundException.notFoundWithOrderId(id).get();
        }
    }

    private CollectionModel<OrderDto> addLinksToOrder(List<OrderDto> orderDtoList) {
        for (final OrderDto orderDto : orderDtoList) {
            Link selfLink = linkTo(methodOn(OrderController.class)
                    .readOrderById(orderDto.getId())).withSelfRel();
            orderDto.add(selfLink);
        }
        Link link = linkTo(OrderController.class).withSelfRel();
        return CollectionModel.of(orderDtoList, link);
    }
}
