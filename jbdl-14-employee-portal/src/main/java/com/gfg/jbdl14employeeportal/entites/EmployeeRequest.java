package com.gfg.jbdl14employeeportal.entites;

import lombok.*;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeRequest {
    private String name;
    private Long salary;
    private Float rating;
    private String password;
    private EmployeeType type;
}
