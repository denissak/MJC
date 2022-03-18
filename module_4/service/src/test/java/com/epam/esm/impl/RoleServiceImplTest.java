package com.epam.esm.impl;

import com.epam.esm.RoleService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.mapper.RoleMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class RoleServiceImplTest {

    private static RoleMapper roleMapper;
    private RoleRepository roleRepository;
    private static RoleService roleServiceImpl;
    private static final Long ROLE_ID_1 = 1L;
    private RoleEntity roleEntity;
    private RoleDto roleDto;
    private final List<RoleEntity> roleEntityList = new ArrayList<>();
    private final List<RoleDto> roleDtoList = new ArrayList<>();
    private Page<RoleEntity> roleEntityPage;
    private Page<RoleDto> roleDtoPage;
    private Pageable pageable;

    @BeforeAll
    public static void init() {
        roleMapper = RoleMapper.INSTANCE;
    }

    @BeforeEach
    public void setUp() {
        roleRepository = mock(RoleRepository.class);
        roleServiceImpl = new RoleServiceImpl(roleRepository, roleMapper);

        roleEntity = RoleEntity.builder()
                .id(ROLE_ID_1)
                .name("ADMIN")
                .build();

        roleDto = RoleDto.builder()
                .id(ROLE_ID_1)
                .name("ADMIN")
                .build();

        pageable = PageRequest.of(0,1000, Sort.by("id"));

        roleEntityList.add(roleEntity);
        roleDtoList.add(roleDto);

        roleEntityPage = new PageImpl<>(roleEntityList);
        roleDtoPage = new PageImpl<>(roleDtoList);
    }

    @Test
    void create() {
        RoleDto expected = roleDto;
        when(roleRepository.save(any())).thenReturn(roleEntity);
        RoleDto actual = roleServiceImpl.create(expected);
        Assertions.assertEquals(expected, actual);
        verify(roleRepository).save(any());
    }

    @Test
    void findById() {
        RoleDto expected = roleDto;
        when(roleRepository.findById(ROLE_ID_1)).thenReturn(Optional.ofNullable(roleEntity));
        RoleDto actual = roleServiceImpl.findById(ROLE_ID_1);
        Assertions.assertEquals(expected, actual);
        verify(roleRepository).findById(anyLong());
    }

    @Test
    void findAll() {
        List<RoleDto> expected = roleDtoList;
        when(roleRepository.findAll()).thenReturn(roleEntityList);
        List<RoleDto> actual = roleServiceImpl.findAll();
        Assertions.assertEquals(expected, actual);
        verify(roleRepository).findAll();
    }
}
