package com.epam.esm.dao;

import com.epam.esm.entity.UserEntity;

import java.util.List;

public interface UserRepository {

    UserEntity readById(long userId);

    UserEntity readByLogin(String login);

    List<UserEntity> readAll();
}
