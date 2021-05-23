package com.gfg.jbdl14employeeportal.repository;

import com.gfg.jbdl14employeeportal.entites.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee,Long> {
    Optional<Employee> findByEmployeeId(String employeeId);
}
