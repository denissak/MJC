package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.DateTimeWrapper;
import com.epam.esm.validation.OrderValidator;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    DateTimeWrapper dateTimeWrapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            CertificateRepository certificateRepository, OrderMapper orderMapper,
                            UserMapper userMapper, CertificateMapper certificateMapper, DateTimeWrapper dateTimeWrapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
        this.dateTimeWrapper = dateTimeWrapper;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public OrderDto create(OrderDto orderDto) {
        OrderEntity order = orderRepository.readByName(orderDto.getName());
        if (order != null) {
            throw DuplicateException.tagExists().get();
        }
        OrderValidator.validate(orderDto);
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        orderDto.setDate(now);
        OrderEntity orderEntity = orderMapper.convertToOrder(orderDto);
        orderRepository.create(orderEntity);
        for (CertificateDto certificateDto: orderDto.getCertificateDto()) {
            orderRepository.setCertificatesOnOrder(orderEntity.getId(), certificateDto.getId());
        }
//        Optional<TagEntity> tagExist = tagRepository.readById(tagDto.getId());
//        OrderEntity orderEntity = orderRepository.create(orderMapper.convertToTag(tagDto));
//        return tagMapper.convertToTagDto(tagEntity);
//        return tagMapper.convertToTagDto(tagExist.orElseGet(() -> (tagRepository.create(tagMapper.convertToTag(tagDto)))));
        return null; //TODO
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
        readById(orderId);
        orderRepository.delete(orderId);
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
