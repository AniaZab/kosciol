package com.apka.kosciol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
//@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping("/main")
    public String getHelloWorld(Model model){
        model.addAttribute("name", "ania");
        return "main";
    }
}
