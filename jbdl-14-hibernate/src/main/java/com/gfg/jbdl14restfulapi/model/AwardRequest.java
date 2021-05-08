package com.gfg.jbdl14restfulapi.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AwardRequest {
    String name;
    String year;
}
