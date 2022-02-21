package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    UserRepository userRepository;
    CertificateRepository certificateRepository;
    OrderMapper orderMapper;
    UserMapper userMapper;
    CertificateMapper certificateMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            CertificateRepository certificateRepository, OrderMapper orderMapper,
                            UserMapper userMapper, CertificateMapper certificateMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public OrderDto create(OrderDto orderDto) {
        return null;
    }

    @Override
    public OrderDto readById(Long orderId) {
        OrderEntity orderEntity = orderRepository.readById(orderId);
        if (orderEntity == null){
            throw NotFoundException.notFoundWithOrderId(orderId).get();
        }
        return orderMapper.convertToOrderDTO(orderEntity);
    }

    @Override
    public List<OrderDto> readAll() {
        List<OrderEntity> orderEntityList = orderRepository.readAll();
        List<OrderDto> orderDtoList = new ArrayList<>(orderEntityList.size());
        for (OrderEntity orderEntity : orderEntityList) {
            orderDtoList.add(orderMapper.convertToOrderDTO(orderEntity));
        }
        return orderDtoList;
    }

    @Override
    public void delete(Long orderId) {

    }

    @Override
    public List<OrderDto> readAllOrdersByUserId(long userId) {
        List<OrderEntity> orderEntityList = orderRepository.readAllOrdersByUserId(userId);
        List<OrderDto> orderDtoList = new ArrayList<>(orderEntityList.size());
        for (OrderEntity orderEntity : orderEntityList) {
            orderDtoList.add(orderMapper.convertToOrderDTO(orderEntity));
        }
        return orderDtoList;
    }
}
