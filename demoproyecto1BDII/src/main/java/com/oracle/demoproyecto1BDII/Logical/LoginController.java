package com.oracle.demoproyecto1BDII.Logical;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class LoginController {


    @GetMapping("/")
    public String home(Model model) {

        return "Login";  // Retorna la vista 'Login'
    }
}

