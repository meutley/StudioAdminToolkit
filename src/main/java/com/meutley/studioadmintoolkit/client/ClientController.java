package com.meutley.studioadmintoolkit.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("client")
public class ClientController {

    @GetMapping(value = {"", "/index"})
    public String index() {
        return "client/index";
    }
    
}