package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.dao.OrderRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.CertificateEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    OrderRepository orderRepository;
    UserMapper userMapper;
    OrderMapper orderMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, OrderRepository orderRepository, UserMapper userMapper, OrderMapper orderMapper) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.userMapper = userMapper;
        this.orderMapper = orderMapper;
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
    public UserDto readByLogin(String login) { //TODO is needed?
        UserEntity userEntity = userRepository.readByLogin(login);
        if (userEntity == null){
            throw NotFoundException.notFoundUser().get();
        }
        return userMapper.convertToUserDto(userEntity);
    }

    @Override
    public List<UserDto> readAll() {
        List<UserEntity> userEntities = userRepository.readAll();
        List<UserDto> userDtoList = new ArrayList<>(userEntities.size());
        for (UserEntity userEntity : userEntities) {
            userDtoList.add(userMapper.convertToUserDto(userEntity));
        }
        return userDtoList;
    }
}
