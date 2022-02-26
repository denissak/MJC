package com.epam.esm.mapper;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.UserDto.UserDtoBuilder;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.entity.UserEntity.UserEntityBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-02-25T18:04:19+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.1.jar, environment: Java 11.0.14 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto convertToUserDto(UserEntity userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( userEntity.getId() );
        userDto.login( userEntity.getLogin() );

        return userDto.build();
    }

    @Override
    public UserEntity convertToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserEntityBuilder userEntity = UserEntity.builder();

        userEntity.id( userDto.getId() );
        userEntity.login( userDto.getLogin() );

        return userEntity.build();
    }
}
