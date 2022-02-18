package com.epam.esm.mapper;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", uses = OrderMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "orders", target = "orderDtoList")
    UserDto convertToUserDto(UserEntity userEntity);

    @InheritInverseConfiguration
  //  @Mapping(target = "orders", ignore = true)
    UserEntity convertToUser(UserDto userDto);
}
