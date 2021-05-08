package com.gfg.jbdl14restfulapi.repository;

import com.gfg.jbdl14restfulapi.entity.Movie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie,Long> {
    Optional<Movie> findByMovieName(String name);
    Optional<Movie> findByGenre(String genre);
}
