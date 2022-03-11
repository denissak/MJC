package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.TagEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.ReadOrderMapper;
import com.epam.esm.util.DateTimeWrapper;
import com.epam.esm.validation.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Contains methods implementation for working mostly with {@code OrderDto}
 * entity.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ReadOrderMapper readOrderMapper;
    private final DateTimeWrapper dateTimeWrapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper,
                            DateTimeWrapper dateTimeWrapper, ReadOrderMapper readOrderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.dateTimeWrapper = dateTimeWrapper;
        this.readOrderMapper = readOrderMapper;
    }

    /**
     * Creates and saves the passed order.
     *
     * @param orderDto the order to be saved
     * @return saved order
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public OrderDto create(OrderDto orderDto) {
        OrderEntity order = orderRepository.findByName(orderDto.getName());
        if (order != null) {
            throw DuplicateException.tagExists().get();
        }
        OrderValidator.validate(orderDto);
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        orderDto.setDate(now);
        OrderEntity orderEntity = orderMapper.convertToOrder(orderDto);
        orderRepository.save(orderEntity);
//        for (CertificateDto certificateDto : orderDto.getCertificateDto()) {
//            orderRepository.setCertificatesOnOrder(orderEntity.getId(), certificateDto.getId());
//        }
        return orderMapper.convertToOrderDTO(orderEntity);
    }

    /**
     * Reads order with passed id.
     *
     * @param orderId the id of order to be read
     * @return order with passed id
     */
    @Override
    public OrderDto readById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        if (orderEntity == null) {
            throw NotFoundException.notFoundWithOrderId(orderId).get();
        }
        return orderMapper.convertToOrderDTO(orderEntity);
    }

    /**
     * Reads all users according to passed parameters.
     *
     * @return entities which meet passed parameters
     */
    @Override
    public List<OrderDto> readAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
//        List<OrderEntity> orderEntityList = orderRepository.readAll(page, size);
//        List<OrderDto> orderDtoList = new ArrayList<>(orderEntityList.size());
//        for (OrderEntity orderEntity : orderEntityList) {
//            orderDtoList.add(orderMapper.convertToOrderDTO(orderEntity));
//        }
//        return orderDtoList;
        return orderRepository.findAll(pageable).stream().map(orderMapper::convertToOrderDTO).collect(Collectors.toList());
    }

    /**
     * Deletes orders with passed id.
     *
     * @param orderId the id of order to be deleted
     */
    @Transactional
    @Override
    public void delete(Long orderId) {
        OrderEntity orderEntity = orderMapper.convertToOrder(readById(orderId));
        orderRepository.delete(orderEntity);
    }

    /**
     * Reads all orders by user.
     *
     * @param userId the id of user to be sorted
     * @return orderDto which meet passed parameters
     */
    @Override
    public List<OrderDto> readAllOrdersByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
//        List<OrderEntity> orderEntityList = orderRepository.findAllById(userId, pageable);
//        List<OrderDto> orderDtoList = new ArrayList<>(orderEntityList.size());
//        for (OrderEntity orderEntity : orderEntityList) {
//            orderDtoList.add(orderMapper.convertToOrderDTO(orderEntity));
//        }
//        return orderDtoList;
        return orderRepository.findAllById(userId, pageable).stream().map(orderMapper::convertToOrderDTO).collect(Collectors.toList());
    }

    /**
     * Reads cost and date orders by user.
     *
     * @param userId the id of user to be sorted
     * @return readOrderDto which meet passed parameters
     */
    @Override
    public List<ReadOrderDto> readCostAndDateOrderByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
//        List<OrderEntity> orderEntityList = orderRepository.readAllOrdersByUserId(userId, page, size);
//        List<ReadOrderDto> readOrderDtoList = new ArrayList<>(orderEntityList.size());
//        for (OrderEntity orderEntity : orderEntityList) {
//            readOrderDtoList.add(readOrderMapper.convertToReadOrderDto(orderEntity));
//        }
//        return readOrderDtoList;
        return orderRepository.findAllById(userId, pageable).stream().map(readOrderMapper::convertToReadOrderDto).collect(Collectors.toList());
    }
}
