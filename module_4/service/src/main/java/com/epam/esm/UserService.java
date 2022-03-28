package com.epam.esm;

import com.epam.esm.dto.UserDto;

import java.util.List;

/**
 * Contains methods for working mostly with {@code UserDto} entity.
 */
public interface UserService {

    /**
     * Read user with passed id.
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
     * @return all users
     */
    List<UserDto> readAll(int page, int size);

    /**
     * Reads UserEntity with passed name.
     *
     * @param login the name of entity to be read
     * @return user with passed login
     */
    UserDto readByName(String login);

    /**
     * Create and save the passed user.
     *
     * @param userDto the user to be saved
     * @return saved user
     */
    UserDto create(UserDto userDto);

    /**
     * Delete user with passed id.
     *
     * @param userId the id of user to be deleted
     */
    void delete(long userId);
}
