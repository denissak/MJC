package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserDto.UserDtoBuilder;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.entity.UserEntity.UserEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-03-31T08:19:36+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.13 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    private final RoleMapper roleMapper;

    @Autowired
    public UserMapperImpl(RoleMapper roleMapper) {

        this.roleMapper = roleMapper;
    }

    @Override
    public UserDto convertToUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.roleDto( roleMapper.convertToRoleDto( userEntity.getRoleEntity() ) );
        userDto.id( userEntity.getId() );
        userDto.login( userEntity.getLogin() );
        userDto.password( userEntity.getPassword() );

        return userDto.build();
    }

    @Override
    public UserEntity convertToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.roleEntity( roleMapper.convertToRole( userDto.getRoleDto() ) );
        userEntity.id( userDto.getId() );
        userEntity.login( userDto.getLogin() );
        userEntity.password( userDto.getPassword() );

        return userEntity.build();
    }
}
