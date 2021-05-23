package com.gfg.jbdl14employeeportal.manager;

import com.gfg.jbdl14employeeportal.entites.EmployeeRequest;
import com.gfg.jbdl14employeeportal.entites.EmployeeResponse;
import com.gfg.jbdl14employeeportal.exception.NotFoundException;

public interface EmployeeManager {
    EmployeeResponse create(EmployeeRequest employeeRequest);
    EmployeeResponse get(String employeeId) throws NotFoundException;
    Float getRating(String employeeId) throws NotFoundException;
    Long getSalary(String employeeId) throws NotFoundException;
    void updateRating(String employeeId, Float rating) throws NotFoundException;
    void updateSalary(String employeeId, Long salary) throws NotFoundException;
    void assignManager(String employeeId, String managerId) throws NotFoundException;
}
