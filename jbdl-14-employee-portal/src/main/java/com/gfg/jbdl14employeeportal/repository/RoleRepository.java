package com.gfg.jbdl14employeeportal.repository;

import com.gfg.jbdl14employeeportal.entites.Employee;
import com.gfg.jbdl14employeeportal.entites.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRole(String role);
}
