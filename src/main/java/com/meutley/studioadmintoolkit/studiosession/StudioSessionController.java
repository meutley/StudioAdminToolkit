package com.meutley.studioadmintoolkit.studiosession;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/studio-session")
public class StudioSessionController {

    @GetMapping(value = { "", "/index" })
    public String index(Model model) {
        List<String> studioSessions = new ArrayList<String>();
        model.addAttribute("studioSessions", studioSessions);
        return "studio-session/index";
    }
    
}