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

    // ---------------------------------------------------------
    // VRAI CODE (À activer quand le travail de la BDD sera fini)
    // ---------------------------------------------------------
    // @Autowired
    // private ItemRepository itemRepository;

    @GetMapping
    public String browseItems(Model model) {
        
        // --- 1. LE VRAI CODE FUTUR (Actuellement en pause) ---
        // List<Item> items = itemRepository.findAll();
        // model.addAttribute("items", items);

        // --- 2. LE MOCK ACTUEL (Pour que ton design s'affiche) ---
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
        // Plus tard, on ajoutera ici : itemRepository.save(newItem);
        return "redirect:/items";
    }
}