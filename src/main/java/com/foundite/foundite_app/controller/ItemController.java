package com.foundite.foundite_app.controller;

import com.foundite.foundite_app.model.Item;
import com.foundite.foundite_app.model.User;
import com.foundite.foundite_app.service.ItemService;
import com.foundite.foundite_app.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    public ItemController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping
    public String listItems(@RequestParam(required = false) String category,
                            @RequestParam(required = false) String search,
                            Model model) {
        if (search != null && !search.isBlank()) {
            model.addAttribute("items", itemService.search(search));
        } else if (category != null && !category.isBlank()) {
            model.addAttribute("items", itemService.findByCategory(category));
        } else {
            model.addAttribute("items", itemService.findAll());
        }
        return "items/list";
    }

    @GetMapping("/{id}")
    public String showItem(@PathVariable Long id, Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        model.addAttribute("item", item);
        return "items/detail";
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/form";
    }

    @PostMapping
    public String createItem(@ModelAttribute Item item,
                             @AuthenticationPrincipal UserDetails currentUser) {
        User user = userService.findByUsername(currentUser.getUsername());
        item.setPostedBy(user);
        itemService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String editItemForm(@PathVariable Long id,
                               @AuthenticationPrincipal UserDetails currentUser,
                               Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        if (!canEdit(item, currentUser)) {
            return "redirect:/items/" + id;
        }
        model.addAttribute("item", item);
        return "items/form";
    }

    @PostMapping("/{id}")
    public String updateItem(@PathVariable Long id,
                             @ModelAttribute Item form,
                             @AuthenticationPrincipal UserDetails currentUser) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        if (!canEdit(item, currentUser)) {
            return "redirect:/items/" + id;
        }
        item.setTitle(form.getTitle());
        item.setDescription(form.getDescription());
        item.setCategory(form.getCategory());
        item.setLocation(form.getLocation());
        item.setFoundDate(form.getFoundDate());
        item.setStatus(form.getStatus());
        itemService.save(item);
        return "redirect:/items/" + id;
    }

    private boolean canEdit(Item item, UserDetails currentUser) {
        if (currentUser == null) return false;
        boolean isOwner = item.getPostedBy() != null
                && item.getPostedBy().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return isOwner || isAdmin;
    }
}
