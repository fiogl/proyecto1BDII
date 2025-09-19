package com.oracle.demoproyecto1BDII.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @GetMapping("/home")
    public String home() {
        return "home";  // Retorna la vista 'home'
    }
}