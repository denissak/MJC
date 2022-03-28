package com.epam.esm;

import com.epam.esm.dto.RoleDto;

import java.util.List;

/**
 * Contains methods for working mostly with {@code RoleDto} entity.
 */
public interface RoleService {

    /**
     * Create and save the passed role.
     *
     * @param roleDto the role to be saved
     * @return saved role
     */
    RoleDto create (RoleDto roleDto);

    /**
     * Read role with passed id.
     *
     * @param roleId id of role to be read
     * @return role with passed id
     */
    RoleDto findById(long roleId);

    /**
     * Read all roles.
     *
     * @return all roles.
     */
    List<RoleDto> findAll ();
}
