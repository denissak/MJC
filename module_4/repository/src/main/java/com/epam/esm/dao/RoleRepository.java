package com.epam.esm.dao;

import com.epam.esm.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains methods for working mostly with {@code Role} entity.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
