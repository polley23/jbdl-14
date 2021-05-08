package com.gfg.jbdl14restfulapi.service;

import com.gfg.jbdl14restfulapi.NotFoundException;
import com.gfg.jbdl14restfulapi.model.MovieRequest;
import com.gfg.jbdl14restfulapi.entity.Movie;
import com.gfg.jbdl14restfulapi.model.MovieResponse;

public interface MovieService {

    void create(MovieRequest movieRequest);
    MovieResponse get(String name) throws NotFoundException;
    void update(String name, MovieRequest movieRequest) throws NotFoundException;
    void delete(String name) throws NotFoundException;
}
