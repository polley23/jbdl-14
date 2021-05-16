package com.gfg.jbdl14springsecurity.repository;

import com.gfg.jbdl14springsecurity.entities.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}
