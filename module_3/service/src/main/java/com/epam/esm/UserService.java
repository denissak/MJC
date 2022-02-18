package com.epam.esm;

import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;

import java.util.List;

public interface UserService {

    UserDto readById(long userId);

    UserDto readByLogin(String login);

    List<UserDto> readAll();
}
