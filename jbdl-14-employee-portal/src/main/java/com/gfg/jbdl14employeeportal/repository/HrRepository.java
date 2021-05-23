package com.gfg.jbdl14employeeportal.repository;

import com.gfg.jbdl14employeeportal.entites.HumanResource;
import com.gfg.jbdl14employeeportal.entites.Manager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HrRepository extends CrudRepository<HumanResource,Long> {
    Optional<HumanResource> findByEmployeeId(String employeeId);
}
