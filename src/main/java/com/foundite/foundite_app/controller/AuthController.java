package com.foundite.foundite_app.controller;

import com.foundite.foundite_app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }

    @PostMapping("/register")
    public String processRegistration(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String fullName,
            Model model) {
        try {
            userService.registerNewUser(username, email, password, fullName);
            return "redirect:/login?registered=true";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/register";
        }
    }
}