package com.epam.esm.mapper;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.RoleDto.RoleDtoBuilder;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.entity.RoleEntity.RoleEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-14T18:53:10+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleEntity convertToRole(RoleDto roleDto) {
        if ( roleDto == null ) {
            return null;
        }

        RoleEntityBuilder roleEntity = RoleEntity.builder();

        roleEntity.id( roleDto.getId() );
        roleEntity.name( roleDto.getName() );

        return roleEntity.build();
    }

    @Override
    public RoleDto convertToRoleDto(RoleEntity roleEntity) {
        if ( roleEntity == null ) {
            return null;
        }

        RoleDtoBuilder roleDto = RoleDto.builder();

        roleDto.id( roleEntity.getId() );
        roleDto.name( roleEntity.getName() );

        return roleDto.build();
    }
}
