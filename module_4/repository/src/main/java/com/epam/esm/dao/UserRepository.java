package com.epam.esm.dao;

import com.epam.esm.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains methods for working mostly with {@code User} entity.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long>{

    UserEntity findByLogin(String login);


//    /**
//     * Reads user with passed id.
//     *
//     * @param userId the id of entity to be read
//     * @return entity with passed id
//     */
//    UserEntity readById(long userId);
//
//    /**
//     * Reads all users according to passed parameters.
//     *
//     * @return entities which meet passed parameters
//     */
//    List<UserEntity> readAll(int page, int size);
//
//    UserEntity readByName(String login);
//
//    void createAutoUser(UserEntity userEntity);
//
//    UserEntity create(UserEntity userEntity);
}
