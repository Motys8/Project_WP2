package com.foundite.foundite_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // USER STORY 3: Admin Dashboard (Review listings)
    @GetMapping("/dashboard")
    public String adminDashboard(Model model) {
        // TODO: Call a Service to fetch all items (including unvalidated ones) and system stats
        
        // Returns the HTML view located at src/main/resources/templates/admin/dashboard.html
        return "admin/dashboard";
    }

    // USER STORY 3: Delete or moderate an inappropriate listing
    @PostMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        // TODO: Call a Service to delete the item with the given 'id' from the database
        
        // Redirects back to the dashboard after deletion
        return "redirect:/admin/dashboard";
    }

    // USER STORY 3: Manage user accounts
    @GetMapping("/users")
    public String manageUsers(Model model) {
        // TODO: Call a Service to fetch the list of all registered users
        
        // Returns the HTML view located at src/main/resources/templates/admin/users.html
        return "admin/users";
    }
}