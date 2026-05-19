package com.foundite.foundite_app.controller;

import com.foundite.foundite_app.repository.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/items")
    public String listItems(@RequestParam(required = false) String category, Model model) {
        if (category != null && !category.isBlank()) {
            model.addAttribute("items", itemRepository.findByCategory(category));
        } else {
            model.addAttribute("items", itemRepository.findAll());
        }
        // TODO: create Thymeleaf template items/list.html
        return "items/list";
    }
}
