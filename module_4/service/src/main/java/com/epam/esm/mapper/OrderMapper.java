package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.OrderEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = {CertificateMapper.class, UserMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface OrderMapper {

    CertificateMapper INSTANCE = Mappers.getMapper(CertificateMapper.class);

    @Mapping(source = "certificateEntities", target = "certificateDto")
    @Mapping(source = "userEntity", target = "userDto")
    OrderDto convertToOrderDto(OrderEntity orderEntity);

//    @InheritInverseConfiguration
//    @Mapping(target = "certificateEntities", ignore = true)
//    @Mapping(target = "userEntity", ignore = true)
    OrderEntity convertToOrder(OrderDto orderDto);
}
