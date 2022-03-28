package com.epam.esm.dao;

import com.epam.esm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains methods for working mostly with {@code User} entity.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    /**
     * Reads UserEntity with passed name.
     *
     * @param login the name of entity to be read
     * @return user with passed login
     */
    UserEntity findByLogin(String login);
}
