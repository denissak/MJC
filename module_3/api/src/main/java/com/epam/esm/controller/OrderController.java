package com.epam.esm.controller;

import com.epam.esm.OrderService;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<OrderDto> readsAllOrders() {
        return orderService.readAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto readOrderById(@PathVariable long id) {
        return orderService.readById(id);
    }

    @GetMapping("user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> readOrderByUserId(@PathVariable long id) {
        return orderService.readAllOrdersByUserId(id);
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
}
