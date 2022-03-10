package com.epam.esm;

import com.epam.esm.dto.UserDto;

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
     * @param page numbers of page
     * @param size number of elements per page
     *
     * @return all users
     */
    List<UserDto> readAll(int page, int size);
}
