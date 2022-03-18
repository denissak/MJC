package com.epam.esm.impl;

import com.epam.esm.RoleService;
import com.epam.esm.dao.RoleRepository;
import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.RoleEntity;
import com.epam.esm.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contains methods implementation for working mostly with {@code RoleDto}
 * entity.
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Create and save the passed role.
     *
     * @param roleDto the role to be saved
     * @return saved role
     */
    @Override
    public RoleDto create(RoleDto roleDto) {
        RoleEntity roleEntity = roleMapper.convertToRole(roleDto);
        return roleMapper.convertToRoleDto(roleRepository.save(roleEntity));
    }

    /**
     * Read role with passed id.
     *
     * @param roleId the id of role to be read
     * @return role with passed id
     */
    @Override
    public RoleDto findById(long roleId) {
        return roleMapper.convertToRoleDto(roleRepository.findById(roleId).get());
    }

    /**
     * Read all roles according to passed parameters.
     *
     * @return entities which meet passed parameters
     */
    @Override
    public List<RoleDto> findAll() {
        return roleRepository.findAll().stream().map(roleMapper::convertToRoleDto).collect(Collectors.toList());
    }
}
