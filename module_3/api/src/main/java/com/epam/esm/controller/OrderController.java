package com.epam.esm.controller;

import com.epam.esm.OrderService;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> readsAllOrders(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                    @RequestParam(value = "size", defaultValue = "5",required = false) int size) {
        List<OrderDto> orderDtoList = orderService.readAll(page, size);
        return addLinksToOrder(orderDtoList);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readOrderById(@PathVariable long id) {
        Link link = linkTo(OrderController.class).withSelfRel();
        OrderDto orderDto = orderService.readById(id);
        orderDto.add(link);
        return orderDto;
    }

    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<OrderDto> readOrderByUserId(@PathVariable long id,
                                                       @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                       @RequestParam(value = "size", defaultValue = "5",required = false) int size) {
        List<OrderDto> orderDtoList = orderService.readAllOrdersByUserId(id, page, size);
        return addLinksToOrder(orderDtoList);
    }

    @GetMapping("user/{id}/status-order")
    @ResponseStatus(HttpStatus.OK)
    public CollectionModel<ReadOrderDto> readCostAndDateByUserId(@PathVariable long id,
                                                                 @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                 @RequestParam(value = "size", defaultValue = "5",required = false) int size) {
        List<ReadOrderDto> readOrderDtoList = orderService.readCostAndDateOrderByUserId(id, page, size);
        for (final ReadOrderDto readOrderDto: readOrderDtoList){
            Link selfLink = linkTo(methodOn(OrderController.class).readOrderById(readOrderDto.getId())).withSelfRel();
            readOrderDto.add(selfLink);
        }
        Link link = linkTo(OrderController.class).withSelfRel();
        return CollectionModel.of(readOrderDtoList,link);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createCertificate(@RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCertificate(@PathVariable long id) {
        orderService.delete(id);
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
