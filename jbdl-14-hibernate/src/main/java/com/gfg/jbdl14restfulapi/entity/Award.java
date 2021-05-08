package com.gfg.jbdl14restfulapi.entity;


import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "award")
public class Award {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name" , nullable = false)
    private String name;
    @Column(name = "year" , nullable = false)
    private String year;
}
