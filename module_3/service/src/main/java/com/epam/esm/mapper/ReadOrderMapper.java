package com.epam.esm.mapper;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.ReadOrderDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.OrderEntity;
import com.epam.esm.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ReadOrderMapper {
    ReadOrderMapper INSTANCE = Mappers.getMapper(ReadOrderMapper.class);

    ReadOrderDto convertToReadOrderDto(OrderEntity orderEntity);

}
