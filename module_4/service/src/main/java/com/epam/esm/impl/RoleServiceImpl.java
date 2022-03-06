package com.epam.esm.impl;

import com.epam.esm.RoleService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public RoleDto create(RoleEntity roleEntity) {
        return null;
    }

    @Override
    public RoleDto findById(long roleId) {
        return null;
    }

    @Override
    public List<RoleDto> findAll() {
        return null;
    }
}
