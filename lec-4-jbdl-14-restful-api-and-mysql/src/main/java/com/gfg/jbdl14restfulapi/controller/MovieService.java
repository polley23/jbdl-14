package com.gfg.jbdl14restfulapi.controller;

public interface MovieService {

    void create(MovieRequest movieRequest);
    Movie get(String name);
    void update(String name, MovieRequest movieRequest);
    void delete(String name);
}
