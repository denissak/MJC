package com.epam.esm.impl;

import com.epam.esm.UserService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dao.UserRepository;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.mapper.RoleMapper;
import com.epam.esm.mapper.RoleMapperImpl;
import com.epam.esm.mapper.UserMapper;
import com.epam.esm.mapper.UserMapperImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    private static UserMapper userMapper = new UserMapperImpl(new RoleMapperImpl());
    private static RoleMapper roleMapper = new RoleMapperImpl();
    private static UserService userServiceImpl;
    private static final Long USER_ID_1 = 1L;

    private UserEntity userEntity;
    private UserDto userDto;
    private RoleEntity roleEntity;
    private RoleDto roleDto;
    private List<UserDto> userDtoList = new ArrayList<>();
    private List<UserEntity> userEntityList = new ArrayList<>();
    private Pageable pageable;
    private Page<UserEntity> userEntityPage;

    @BeforeAll
    public static void init () {
        userMapper = new UserMapperImpl(roleMapper);
        roleMapper = new RoleMapperImpl();

    }

    @BeforeEach
    public void setUp () {
        userRepository = mock(UserRepository.class);
        roleRepository = mock(RoleRepository.class);
        userServiceImpl = new UserServiceImpl(userRepository, userMapper, roleRepository, roleMapper, passwordEncoder);

        roleEntity = RoleEntity.builder()
                .id(2L)
                .name("USER")
                .build();

        roleDto = RoleDto.builder()
                .id(2L)
                .name("USER")
                .build();

        userEntity = UserEntity.builder()
                .id(USER_ID_1)
                .login("user1")
                .build();

        userEntity = UserEntity.builder()
                .id(USER_ID_1)
                .login("user1")
                .password("123")
                .roleEntity(roleEntity)
                .build();

        userDto = UserDto.builder()
                .id(USER_ID_1)
                .login("user1")
                .password("123")
                .roleDto(roleDto)
                .build();

        userEntityList.add(userEntity);
        userDtoList.add(userDto);
        userEntityPage = new PageImpl<>(userEntityList);
        pageable = PageRequest.of(0,1000, Sort.by("id"));
    }

    @Test
    void readById() {
        UserDto expected = userDto;
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.ofNullable(userEntity));
        UserDto actual = userServiceImpl.readById(USER_ID_1);
        Assertions.assertEquals(expected, actual);
        verify(userRepository).findById(anyLong());
    }

    @Test
    void readAll() {
        List<UserDto> expected = userDtoList;
        when(userRepository.findAll(pageable)).thenReturn(userEntityPage);
        List<UserDto> actual = userServiceImpl.readAll(0, 1000);
        Assertions.assertEquals(expected, actual);
        verify(userRepository).findAll(pageable);
    }

    @Test
    void testDeleteUser() {
        when(userRepository.findById(USER_ID_1)).thenReturn(Optional.ofNullable(userEntity));
        userServiceImpl.delete(USER_ID_1);
        verify(userRepository).delete(userEntity);
    }
}
