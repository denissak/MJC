package com.epam.esm.mapper;

import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.OrderEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    private final OrderRepository orderRepository;
    private final UserMapper userMapper;
    private final CertificateMapper certificateMapper;

    @Autowired
    public OrderMapper(OrderRepository orderRepository, UserMapper userMapper, CertificateMapper certificateMapper) {
        this.orderRepository = orderRepository;
        this.userMapper = userMapper;
        this.certificateMapper = certificateMapper;
    }

    public OrderDto convertToOrderDTO(OrderEntity orderEntity){
        List<CertificateDto> certificateDtoList = new ArrayList<>();
        orderEntity.getOrderCertificateEntityList().forEach(orderCertificateEntity ->
                certificateDtoList.add(certificateMapper.convertToCertificateDto(orderCertificateEntity.getCertificateEntity()))
        );
        return OrderDto.builder()
                .id(orderEntity.getId())
                .name(orderEntity.getName())
                .cost(orderEntity.getCost())
                .date(orderEntity.getDate())
                .userDto(new UserDto(orderEntity.getUserEntity().getId(), orderEntity.getUserEntity().getLogin()))
                .certificateDto(certificateDtoList)
                .build();
    }

}
