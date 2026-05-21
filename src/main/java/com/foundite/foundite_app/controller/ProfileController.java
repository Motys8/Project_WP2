package com.foundite.foundite_app.controller;

import com.foundite.foundite_app.model.User;
import com.foundite.foundite_app.service.ItemService;
import com.foundite.foundite_app.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    private final UserService userService;
    private final ItemService itemService;

    public ProfileController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserDetails currentUser, Model model) {
    User user = userService.findByUsername(currentUser.getUsername());
    model.addAttribute("username", user.getUsername());
    model.addAttribute("email", user.getEmail());
    model.addAttribute("fullName", user.getFullName());
    model.addAttribute("role", user.getRoles().contains("ROLE_ADMIN") ? "Admin" : "User");
    model.addAttribute("createdAt", user.getCreatedAt());
    model.addAttribute("userItems", itemService.findByUser(user));
    model.addAttribute("itemCount", itemService.countByUser(user));
    return "profile";
    }
}