package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserDto readById(long userId) {
        UserEntity userEntity = userRepository.readById(userId);
        if (userEntity == null){
            throw NotFoundException.notFoundWithUserId(userId).get();
        }
        return userMapper.convertToUserDto(userEntity);
    }

    @Override
    public List<UserDto> readAll(int page, int size) {
        List<UserEntity> userEntities = userRepository.readAll(page, size);
        List<UserDto> userDtoList = new ArrayList<>(userEntities.size());
        for (UserEntity userEntity : userEntities) {
            userDtoList.add(userMapper.convertToUserDto(userEntity));
        }
        return userDtoList;
    }
}
