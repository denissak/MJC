package com.epam.esm.dao;

import com.epam.esm.entity.UserEntity;

import java.util.List;

/**
 * Contains methods for working mostly with {@code User} entity.
 */
public interface UserRepository {

    /**
     * Reads user with passed id.
     *
     * @param userId the id of entity to be read
     * @return entity with passed id
     */
    UserEntity readById(long userId);

    /**
     * Reads all users according to passed parameters.
     *
     * @param page numbers of page
     * @param size number of elements per page
     *
     * @return entities which meet passed parameters
     */
    List<UserEntity> readAll(int page, int size);
}
