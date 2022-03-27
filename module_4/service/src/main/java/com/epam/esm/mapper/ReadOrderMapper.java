package com.epam.esm.mapper;

import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ReadOrderMapper {

    ReadOrderMapper INSTANCE = Mappers.getMapper(ReadOrderMapper.class);

    ReadOrderDto convertToReadOrderDto(OrderEntity orderEntity);

}
