package com.foundite.foundite_app.controller;

import com.foundite.foundite_app.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/items")
    public String listItems(@RequestParam(required = false) String category, Model model) {
        if (category != null && !category.isBlank()) {
            model.addAttribute("items", itemService.findByCategory(category));
        } else {
            model.addAttribute("items", itemService.findAll());
        }
        return "items/list";
    }
}
