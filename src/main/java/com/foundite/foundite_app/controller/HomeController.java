package com.foundite.foundite_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Redirige localhost:8080 vers localhost:8080/items
        return "redirect:/items";
    }
}