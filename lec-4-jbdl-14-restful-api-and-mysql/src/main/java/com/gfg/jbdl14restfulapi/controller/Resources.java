package com.gfg.jbdl14restfulapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    Movie getMovie(@RequestParam("name") String name){
        return movieService.get(name);
    }

    @PutMapping("/v1/movie/{name}")
    void updateMovie(@PathVariable("name") String name,
    @RequestBody MovieRequest movieRequest ){
        movieService.update(name,movieRequest);
    }

    @DeleteMapping("/v1/movie/{name}")
    void deleteMovie(@PathVariable("name") String name,
                     @RequestBody MovieRequest movieRequest ) throws Exception {
       movieService.delete(name);
    }
}
