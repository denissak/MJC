package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.exception.DuplicateException;
import com.epam.esm.exception.NotFoundException;
import com.epam.esm.mapper.RoleMapper;
import com.epam.esm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains methods implementation for working mostly with {@code UserDto}
 * entity.
 */
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

    /**
     * Authorization user.
     *
     * @param username the user's name that try authorization
     * @return userDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userMapper.convertToUserDto(userRepository.findByLogin(username));
        if (userDto == null) {
            throw NotFoundException.notFoundUser().get();
        }
        return new User(userDto.getLogin(), userDto.getPassword(), userDto.getAuthorities());
    }

    /**
     * Create and save the passed user.
     *
     * @param userDto the user to be saved
     * @return saved user
     */
    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        UserEntity userEntity = userRepository.findByLogin(userDto.getLogin());
        if (userEntity != null) {
            throw DuplicateException.userExists().get();
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity = userMapper.convertToUser(userDto);
        userEntity.setRoleEntity(RoleEntity.builder()
                        .id(2L)
                        .name("USER")
                        .build());
        return userMapper.convertToUserDto(userRepository.save(userEntity));
    }

    /**
     * Read user with passed id.
     *
     * @param userId the id of user to be read
     * @return user with passed id
     */
    @Override
    public UserDto readById(long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        return userMapper.convertToUserDto(userEntity);
    }

    /**
     * Read all users.
     *
     * @param page numbers of page
     * @param size number of elements per page
     * @return all users
     */
    @Override
    public List<UserDto> readAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        return userRepository.findAll(pageable).stream().map(userMapper::convertToUserDto).collect(Collectors.toList());
    }

    /**
     * Delete user with passed id.
     *
     * @param userId the id of user to be deleted
     */
    @Override
    public void delete(long userId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        userRepository.delete(userEntity);
    }

    /**
     * Read User with passed name.
     *
     * @param login the name of entity to be read
     * @return user with passed login
     */
    @Override
    public UserDto readByName(String login) {
        UserEntity userEntity = userRepository.findByLogin(login);
        return userMapper.convertToUserDto(userEntity);
    }
}
