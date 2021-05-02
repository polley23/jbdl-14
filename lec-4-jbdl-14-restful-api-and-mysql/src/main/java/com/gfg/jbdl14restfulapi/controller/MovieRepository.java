package com.gfg.jbdl14restfulapi.controller;

import java.util.List;

public interface MovieRepository {
    void save(Movie movie);
    Movie findByName(String name);
    List<Movie> findByLanguage(String language);
    List<Movie> findByGenre(String genre);
    void delete(String name);
}
