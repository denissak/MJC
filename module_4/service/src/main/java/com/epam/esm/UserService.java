package com.epam.esm;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;

import java.util.List;

/**
 * Contains methods for working mostly with {@code UserDto} entity.
 */
public interface UserService {

    /**
     * Reads user with passed id.
     *
     * @param userId the id of user to be read
     * @return user with passed id
     */
    UserDto readById(long userId);

    /**
     * Reads all users.
     *
     * @return all users
     */
    List<UserDto> readAll(int page, int size);

    UserDto readByName (String login);

    UserDto create(UserDto userDto);
}
