package com.foundite.foundite_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/items")
public class ItemController {

    @GetMapping
    public String browseItems(Model model) {
        
        // Tes fausses données pour voir le design de tes cartes Bootstrap
        List<Map<String, String>> itemsList = List.of(
            Map.of("title", "Blue Nike Backpack", "category", "Bags", "location", "Cafeteria", "date", "May 19", "icon", "bi-backpack"),
            Map.of("title", "Car Keys (Renault)", "category", "Keys", "location", "North Parking", "date", "May 18", "icon", "bi-key"),
            Map.of("title", "Black Water Bottle", "category", "Accessories", "location", "Room 204", "date", "May 17", "icon", "bi-cup-straw")
        );
        model.addAttribute("items", itemsList);
        
        return "items/gallery"; 
    }

    @GetMapping("/report")
    public String showReportForm() {
        return "items/report-form";
    }

    @PostMapping("/report")
    public String submitReportedItem() {
        // Le bouton "Publier" renvoie simplement à la galerie pour le moment
        return "redirect:/items";
    }
}