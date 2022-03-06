package com.epam.esm;

import com.epam.esm.dto.RoleDto;
import com.epam.esm.entity.RoleEntity;

import java.util.List;

public interface RoleService {

    RoleDto create (RoleEntity roleEntity);

    RoleDto findById(long roleId);

    List<RoleDto> findAll ();

}
