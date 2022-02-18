package com.epam.esm.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface OrderMapper {
}
