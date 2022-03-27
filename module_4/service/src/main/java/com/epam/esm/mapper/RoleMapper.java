package com.epam.esm.mapper;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.RoleEntity;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {

    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

 //   RoleDto convertToRoleDto(RoleEntity roleEntity);

    RoleEntity convertToRole(RoleDto roleDto);

    RoleDto convertToRoleDto(RoleEntity roleEntity);
}
