package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.RoleMapper;
import com.epam.esm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains methods implementation for working mostly with {@code UserDto}
 * entity.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository, RoleMapper roleMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto register(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setRoleDto(RoleDto.builder()
                .name("User")
                .build());
        return null;
    }

    /**
     * Reads user with passed id.
     *
     * @param userId the id of user to be read
     * @return user with passed id
     */
    @Override
    public UserDto readById(long userId) {
        UserEntity userEntity = userRepository.readById(userId);
        if (userEntity == null){
            throw NotFoundException.notFoundWithUserId(userId).get();
        }
        return userMapper.convertToUserDto(userEntity);
    }

    /**
     * Reads all users.
     *
     * @return all users
     */
    @Override
    public List<UserDto> readAll(int page, int size) {
        List<UserEntity> userEntities = userRepository.readAll(page, size);
        List<UserDto> userDtoList = new ArrayList<>(userEntities.size());
        for (UserEntity userEntity : userEntities) {
            userDtoList.add(userMapper.convertToUserDto(userEntity));
        }
        return userDtoList;
    }

    @Override
    public UserDto readByName(String login) {
        UserEntity userEntity = userRepository.readByName(login);
        return userMapper.convertToUserDto(userEntity);
    }
}
