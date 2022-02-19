package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {UserMapper.class, CertificateMapper.class})
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

        @Mapping(source = "userEntity", target = "userDto")
        @Mapping(source = "certificateEntity", target = "certificateDto")
    OrderDto convertToOrderDto(OrderEntity orderEntity);

        @InheritInverseConfiguration
    //  @Mapping(target = "orders", ignore = true)
    OrderEntity convertToOrder(OrderDto orderDto);
}
