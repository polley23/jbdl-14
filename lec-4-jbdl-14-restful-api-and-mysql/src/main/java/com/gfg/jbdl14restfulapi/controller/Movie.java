package com.gfg.jbdl14restfulapi.controller;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Movie {
    private String name;
    private String genre;
    private String language;
}

//1. application layer - controller @RestController
//2. Service layer - Business logic @Service
//3. Repository layer - Data (database operations) @Repository