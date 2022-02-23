package com.epam.esm;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.entity.OrderEntity;

import java.util.List;

public interface OrderService {

    OrderDto create(OrderDto orderDto);

    OrderDto readById(Long orderId);

    List<OrderDto> readAll(int page, int size);

    void delete(Long orderId);

    List<OrderDto> readAllOrdersByUserId (long userId, int page, int size);

    List<ReadOrderDto> readCostAndDateOrderByUserId(long userId, int page, int size);
}
