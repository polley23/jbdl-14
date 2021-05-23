package com.gfg.jbdl14employeeportal.manager;


import com.gfg.jbdl14employeeportal.entites.*;
import com.gfg.jbdl14employeeportal.exception.NotFoundException;
import com.gfg.jbdl14employeeportal.repository.EmployeeRepository;
import com.gfg.jbdl14employeeportal.repository.HrRepository;
import com.gfg.jbdl14employeeportal.repository.ManagerRepository;
import com.gfg.jbdl14employeeportal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.UUID;

@Service
public class EmployeeManagerImpl implements EmployeeManager{
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ManagerRepository managerRepository;
    @Autowired
    HrRepository hrRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponse create(EmployeeRequest employeeRequest) {
        Employee resp = null;
        switch (employeeRequest.getType()) {
            case EMPLOYEE:
                Role employeeRole = roleRepository.findByRole("employee")
                        .orElse(Role.builder().role("employee").build());
                Employee employee = Employee.builder()
                        .name(employeeRequest.getName())
                        .employeeId(employeeRequest.getName() + UUID.randomUUID().toString().substring(0,3))
                        .rating(employeeRequest.getRating())
                        .salary(employeeRequest.getSalary())
                        .roles(Arrays.asList(employeeRole))
                        .password(passwordEncoder.encode(employeeRequest.getPassword()))
                        .build();
                resp = employeeRepository.save(employee);
                break;
            case MANAGER:
                employeeRole = roleRepository.findByRole("employee")
                        .orElse(Role.builder().role("employee").build());
                Role managerRole = roleRepository.findByRole("manager")
                        .orElse(Role.builder().role("manager").build());
                Manager manager = Manager.builder()
                        .name(employeeRequest.getName())
                        .employeeId(employeeRequest.getName() + UUID.randomUUID().toString().substring(0,3))
                        .rating(employeeRequest.getRating())
                        .salary(employeeRequest.getSalary())
                        .roles(Arrays.asList(employeeRole,managerRole))
                        .password(passwordEncoder.encode(employeeRequest.getPassword()))

                        .build();
                resp = managerRepository.save(manager);
                break;
            case HR:
                employeeRole = roleRepository.findByRole("employee")
                        .orElse(Role.builder().role("employee").build());
                Role hrRole = roleRepository.findByRole("hr")
                        .orElse(Role.builder().role("hr").build());
                HumanResource humanResource = HumanResource.builder()
                        .name(employeeRequest.getName())
                        .employeeId(employeeRequest.getName() + UUID.randomUUID().toString().substring(0,3))
                        .rating(employeeRequest.getRating())
                        .salary(employeeRequest.getSalary())
                        .roles(Arrays.asList(employeeRole,hrRole))
                        .password(passwordEncoder.encode(employeeRequest.getPassword()))
                        .build();
                resp = hrRepository.save(humanResource);
                break;
        }
        return EmployeeResponse.builder()
                .name(resp.getName())
                .employeeId(resp.getEmployeeId())
                .salary(resp.getSalary())
                .rating(resp.getRating())
                .build();
    }

    @Override
    public EmployeeResponse get(String employeeId) throws NotFoundException {
        Employee resp = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new NotFoundException("employee not found"));
        return EmployeeResponse.builder()
                .name(resp.getName())
                .employeeId(resp.getEmployeeId())
                .salary(resp.getSalary())
                .rating(resp.getRating())
                .build();
    }

    @Override
    public Float getRating(String employeeId) throws NotFoundException {
        Employee resp = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new NotFoundException("employee not found"));
        return resp.getRating();
    }

    @Override
    public Long getSalary(String employeeId) throws NotFoundException {
        Employee resp = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new NotFoundException("employee not found"));
        return resp.getSalary();
    }

    @Override
    public void updateRating(String employeeId, Float rating) throws NotFoundException {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new NotFoundException("employee not found"));
        employee.setRating(rating);
        employeeRepository.save(employee);

    }

    @Override
    public void updateSalary(String employeeId, Long salary) throws NotFoundException {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new NotFoundException("employee not found"));
        employee.setSalary(salary);
        employeeRepository.save(employee);
    }

    @Override
    public void assignManager(String employeeId, String managerId) throws NotFoundException {
        Employee employee = employeeRepository.findByEmployeeId(employeeId)
                .orElseThrow(()->new NotFoundException("employee not found"));
        Manager manager = managerRepository.findByEmployeeId(managerId)
                .orElseThrow(()->new NotFoundException("manager not found"));
        if(manager.getSubordinates() == null){
            manager.setSubordinates(Arrays.asList(employee));
        }else{
            manager.getSubordinates().add(employee) ;
        }
        managerRepository.save(manager);

    }
}
