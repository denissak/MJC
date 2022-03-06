package com.epam.esm.dao;

import com.epam.esm.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByName(String name);

    List<RoleEntity> findAll ();
}
