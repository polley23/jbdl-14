package com.gfg.jbdl14restfulapi.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "name" , nullable = false)
    private String movieName;
    private String genre;
    @Column(name = "lang")
    private String language;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    List<Cast> casts;
    @OneToMany(cascade = CascadeType.ALL)
    List<Award> awards;
}