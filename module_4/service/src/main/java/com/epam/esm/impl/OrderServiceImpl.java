package com.epam.esm.impl;

import com.epam.esm.OrderService;
import com.epam.esm.dao.CertificateRepository;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.mapper.CertificateMapper;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.ReadOrderMapper;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.util.DateTimeWrapper;
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
import java.util.stream.Collectors;

/**
 * Contains methods implementation for working mostly with {@code OrderDto}
 * entity.
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final ReadOrderMapper readOrderMapper;
    private final DateTimeWrapper dateTimeWrapper;
    private final CertificateMapper certificateMapper;
    private final UserMapper userMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CertificateRepository certificateRepository, UserRepository userRepository, OrderMapper orderMapper,
                            DateTimeWrapper dateTimeWrapper, ReadOrderMapper readOrderMapper, CertificateMapper certificateMapper, UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.dateTimeWrapper = dateTimeWrapper;
        this.readOrderMapper = readOrderMapper;
        this.certificateMapper = certificateMapper;
        this.userMapper = userMapper;
    }

    /**
     * Create and save the passed order.
     *
     * @param certificateIds the order certificates ids to be saved
     * @param userId the user id that create order to be saved
     * @return saved order
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public OrderDto create(List<Long> certificateIds, long userId) {
        OrderDto orderDto = new OrderDto();
        LocalDateTime now = dateTimeWrapper.wrapDateTime();
        orderDto.setDate(now);
        OrderEntity orderEntity = orderMapper.convertToOrder(orderDto);
        orderEntity.setUserEntity(userRepository.findById(userId).get());
        List<CertificateEntity> certificateEntities = new ArrayList<>();
        Double totalCost = 0.0;

        for (Long certificateId : certificateIds) {
            CertificateEntity certificate = certificateRepository.findById(certificateId).get();
            totalCost += certificate.getPrice();
            certificateEntities.add(certificate);
        }

        orderEntity.setCost(totalCost);
        orderEntity.setName("userId:" + orderEntity.getUserEntity().getId() + "_" + orderEntity.getDate() + orderEntity.getUserEntity().getId());
        orderEntity.setCertificateEntities(certificateEntities);

        orderRepository.save(orderEntity);
        return orderMapper.convertToOrderDto(orderEntity);
    }

    /**
     * Read order with passed id.
     *
     * @param orderId the id of order to be read
     * @return order with passed id
     */
    @Override
    public OrderDto readById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        return orderMapper.convertToOrderDto(orderEntity);
    }

    /**
     * Read all orders according to passed parameters.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return entities which meet passed parameters
     */
    @Override
    public List<OrderDto> readAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return orderRepository.findAll(pageable).stream().map(orderMapper::convertToOrderDto).collect(Collectors.toList());
    }

    /**
     * Delete orders with passed id.
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
     * Read all orders by user.
     *
     * @param userId the id of user to be sorted
     * @param page   numbers of page
     * @param size   number of elements per page
     * @return orderDto which meet passed parameters
     */
    @Override
    public List<OrderDto> readAllOrdersByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return orderRepository.findAllById(userId, pageable).stream().map(orderMapper::convertToOrderDto).collect(Collectors.toList());
    }

    /**
     * Reads cost and date orders by user.
     *
     * @param userId the id of user to be sorted
     * @param page   numbers of page
     * @param size   number of elements per page
     * @return readOrderDto which meet passed parameters
     */
    @Override
    public List<ReadOrderDto> readCostAndDateOrderByUserId(long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return orderRepository.findAllById(userId, pageable).stream().map(readOrderMapper::convertToReadOrderDto).collect(Collectors.toList());
    }
}
