package com.gfg.jbdl14restfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService{
    @Autowired
    MovieRepository movieRepository;

    @Override
    public void create(MovieRequest movieRequest) {
        Movie movie = Movie.builder()
                .genre(movieRequest.getGenre())
                .language(movieRequest.getLanguage())
                .name(movieRequest.getName())
                .build();

        movieRepository.save(movie);
    }

    @Override
    public Movie get(String name) {
        return movieRepository.findByName(name);
    }

    @Override
    public void update(String name, MovieRequest movieRequest) {
        Movie movie = movieRepository.findByName(name);
        if(movieRequest.getGenre() != null)
            movie.setGenre(movieRequest.getGenre());
        if(movieRequest.getLanguage() !=null)
            movie.setLanguage(movieRequest.getLanguage());
        movieRepository.save(movie);
    }

    @Override
    public void delete(String name) {
        Movie movie = movieRepository.findByName(name);
        if(movie!=null) {
            movieRepository.delete(name);
        }
    }
}
