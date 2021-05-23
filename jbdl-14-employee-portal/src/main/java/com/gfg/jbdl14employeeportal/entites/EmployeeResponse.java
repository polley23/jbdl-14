package com.gfg.jbdl14employeeportal.entites;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeResponse {
    private String name;
    private String employeeId;
    private Long salary;
    private Float rating;
}
