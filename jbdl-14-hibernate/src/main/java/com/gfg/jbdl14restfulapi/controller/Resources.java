package com.gfg.jbdl14restfulapi.controller;

import com.gfg.jbdl14restfulapi.NotFoundException;
import com.gfg.jbdl14restfulapi.model.MovieRequest;
import com.gfg.jbdl14restfulapi.model.MovieResponse;
import com.gfg.jbdl14restfulapi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//Movie system
public class Resources {

    @Autowired
    MovieService movieService;

    @PostMapping("/v1/movie")
    void createMovie(@RequestBody MovieRequest movieRequest) throws Exception {//jackson //object mapper
        movieService.create(movieRequest);
    }

    @GetMapping("/v1/movie")// /v1/movie?name=matrix
    MovieResponse getMovie(@RequestParam("name") String name){
        try {
            return movieService.get(name);
        } catch (NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PutMapping("/v1/movie/{name}")
    void updateMovie(@PathVariable("name") String name,
    @RequestBody MovieRequest movieRequest ){
        try {
            movieService.update(name,movieRequest);
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
    }

    @DeleteMapping("/v1/movie/{name}")
    void deleteMovie(@PathVariable("name") String name,
                     @RequestBody MovieRequest movieRequest ) throws Exception {
       movieService.delete(name);
    }
}
