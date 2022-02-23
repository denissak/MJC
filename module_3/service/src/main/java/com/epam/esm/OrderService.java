package com.epam.esm;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    OrderDto readById(Long orderId);

    List<OrderDto> readAll();

    void delete(Long orderId);

    List<OrderDto> readAllOrdersByUserId (long userId);

    public List<ReadOrderDto> readCostAndDateOrderByUserId(long userId);
}
