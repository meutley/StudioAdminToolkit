package com.meutley.studioadmintoolkit.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping(value = {"/", "/index"})
    public String index() {
        return "home/index";
    }
    
}