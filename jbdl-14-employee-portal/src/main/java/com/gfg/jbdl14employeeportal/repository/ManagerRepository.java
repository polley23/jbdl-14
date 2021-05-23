package com.gfg.jbdl14employeeportal.repository;

import com.gfg.jbdl14employeeportal.entites.Employee;
import com.gfg.jbdl14employeeportal.entites.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends CrudRepository<Manager,Long> {
    Optional<Manager> findByEmployeeId(String employeeId);
}
