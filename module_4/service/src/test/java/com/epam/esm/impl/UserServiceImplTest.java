//package com.epam.esm.impl;
//
//import com.epam.esm.UserService;
//import com.epam.esm.dao.UserRepository;
//import com.epam.esm.dto.UserDto;
//import com.epam.esm.entity.UserEntity;
//import com.epam.esm.exception.NotFoundException;
//import com.epam.esm.mapper.UserMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//
//    private UserRepository userRepository;
//    private static UserMapper userMapper;
//    private static UserService userServiceImpl;
//    private static final Long USER_ID_1 = 1L;
//
//    private UserEntity userEntity;
//    private UserDto userDto;
//
//    @BeforeAll
//    public static void init () {
//        userMapper = UserMapper.INSTANCE;
//    }
//
//    @BeforeEach
//    public void setUp () {
//        userRepository = mock(UserRepository.class);
//        userServiceImpl = new UserServiceImpl(userRepository, userMapper, roleRepository, roleMapper);
//
//        userEntity = UserEntity.builder()
//                .id(USER_ID_1)
//                .login("user1")
//                .build();
//
//        userDto = UserDto.builder()
//                .id(USER_ID_1)
//                .login("user1")
//                .build();
//    }
//
//    @Test
//    void readById() {
//        UserDto expected = userDto;
//        when(userRepository.readById(USER_ID_1)).thenReturn(userEntity);
//        UserDto actual = userServiceImpl.readById(USER_ID_1);
//        Assertions.assertEquals(expected, actual);
//        verify(userRepository).readById(anyLong());
//    }
//
//    @Test
//    void readAll() {
//        userServiceImpl.readAll(anyInt(), anyInt());
//        verify(userRepository).readAll(anyInt(), anyInt());
//    }
//
//    @Test
//    void readException() {
//        Assertions.assertThrows(NotFoundException.class, () -> userServiceImpl.readById(USER_ID_1));
//    }
//}