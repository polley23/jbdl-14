package com.gfg.urlshortenerjdbl14.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String clientName;
    @Column(nullable = false)
    private String domain;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "client_id")
    Set<Url> urls;

}
