package com.gfg.jbdl14restfulapi.model;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MovieResponse {
    private String name;
    private String genre;
    private String language;
    private List<CastResponse> castResponse;
    private List<AwardResponse> awardResponses;
}
