package com.gfg.jbdl14employeeportal.reasources;


import com.gfg.jbdl14employeeportal.entites.EmployeeRequest;
import com.gfg.jbdl14employeeportal.exception.NotFoundException;
import com.gfg.jbdl14employeeportal.manager.EmployeeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

@RestController
public class Resource {
    @Autowired
    EmployeeManager employeeManager;

    @PostMapping("/employee")
    @PreAuthorize(value = "hasAnyAuthority('admin','hr')")
    ResponseEntity create(@RequestBody EmployeeRequest employeeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeManager.create(employeeRequest));
    }

    @GetMapping("/employee/{employee_id}")
    @PreAuthorize(value = "hasAnyAuthority('admin','employee','manager','hr')")
    ResponseEntity get(@PathVariable("employee_id") String employeeId, Authentication authentication) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getAuthorities());
        if(authentication.getAuthorities().size()==1){
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(authentication.getAuthorities().iterator().next().getAuthority());
            if(simpleGrantedAuthority.getAuthority().equals("employee")
                    && !usernamePasswordAuthenticationToken.getName().equals( employeeId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        try {
            return ResponseEntity.ok(employeeManager.get(employeeId));
        } catch (NotFoundException e) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/employee/{employee_id}/salary")
    @PreAuthorize(value = "hasAnyAuthority('admin','employee','manager','hr')")
    ResponseEntity getSalary(@PathVariable("employee_id") String employeeId, Authentication authentication) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getAuthorities());
        if(authentication.getAuthorities().size()==1){
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(authentication.getAuthorities().iterator().next().getAuthority());
            if(simpleGrantedAuthority.getAuthority().equals("employee")
                    && !usernamePasswordAuthenticationToken.getName().equals( employeeId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        try {
            return ResponseEntity.ok(employeeManager.getSalary(employeeId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/employee/{employee_id}/rating")
    @PreAuthorize(value = "hasAnyAuthority('admin','employee','manager','hr')")
    ResponseEntity getRating(@PathVariable("employee_id") String employeeId, Authentication authentication) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(),authentication.getAuthorities());
        if(authentication.getAuthorities().size()==1){
            SimpleGrantedAuthority simpleGrantedAuthority =
                    new SimpleGrantedAuthority(authentication.getAuthorities().iterator().next().getAuthority());
            if(simpleGrantedAuthority.getAuthority().equals("employee")
                    && !usernamePasswordAuthenticationToken.getName().equals( employeeId)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        try {
            return ResponseEntity.ok(employeeManager.getRating(employeeId));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PatchMapping("/employee/{employee_id}/salary/{salary}")
    @PreAuthorize(value = "hasAnyAuthority('admin','hr')")
    ResponseEntity updateSalary(@PathVariable("employee_id") String employeeId,
                                @PathVariable("salary") Long salary) {
        try {
            employeeManager.updateSalary(employeeId,salary);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/employee/{employee_id}/rating/{rating}")
    @PreAuthorize(value = "hasAnyAuthority('admin','hr')")
    ResponseEntity updateRating(@PathVariable("employee_id") String employeeId,
                                @PathVariable("rating") Float rating) {
        try {
            employeeManager.updateRating(employeeId,rating);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/employee/{employee_id}/manager/{manager_id}")
    @PreAuthorize(value = "hasAnyAuthority('admin','hr')")
    ResponseEntity assignManager(@PathVariable("employee_id") String employeeId,
                                 @PathVariable("manager_id") String managerId) {
        try {
            employeeManager.assignManager(employeeId,managerId);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

}
