package com.gfg.jbdl14googleauthetication;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Resource {

    @GetMapping("/home")
    String home() {
        return "You are at home page";
    }
}
