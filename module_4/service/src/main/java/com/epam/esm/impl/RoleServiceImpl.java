package com.epam.esm.impl;

import com.epam.esm.RoleService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.entity.UserEntity;
import com.epam.esm.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public RoleDto create(RoleDto roleDto) {
        RoleEntity roleEntity = roleMapper.convertToRole(roleDto);
        return roleMapper.convertToRoleDto(roleRepository.save(roleEntity));
    }

    @Override
    public RoleDto findById(long roleId) {
        return roleMapper.convertToRoleDto(roleRepository.findById(roleId).get());
    }

    @Override
    public List<RoleDto> findAll() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleDto> roleDtoList = new ArrayList<>();
        for (RoleEntity roleEntity : roleEntities) {
            roleDtoList.add(roleMapper.convertToRoleDto(roleEntity));
        }
        return roleDtoList;
    }
}
