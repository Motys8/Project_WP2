package com.foundite.foundite_app.controller;

import com.foundite.foundite_app.model.Item;
import com.foundite.foundite_app.model.User;
import com.foundite.foundite_app.service.ItemService;
import com.foundite.foundite_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final UserService userService;

    private static final String UPLOAD_DIRECTORY = System.getProperty("upload.dir", "/uploads");

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
    public String showItem(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails currentUser,
                           Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        model.addAttribute("item", item);
        model.addAttribute("canEdit", currentUser != null && canEdit(item, currentUser));
        return "items/detail";
    }

    @GetMapping("/new")
    public String newItemForm(Model model) {
        model.addAttribute("item", new Item());
        return "items/form";
    }

    @PostMapping
    public String createItem(@Valid @ModelAttribute Item item,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @AuthenticationPrincipal UserDetails currentUser) {
        if (bindingResult.hasErrors()) {
            return "items/form";
        }

        if (!imageFile.isEmpty()) {
            String imagePath = saveUploadedFile(imageFile);
            item.setImagePath(imagePath);
        }

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
                             @Valid @ModelAttribute Item form,
                             BindingResult bindingResult,
                             @RequestParam("imageFile") MultipartFile imageFile,
                             @AuthenticationPrincipal UserDetails currentUser,
                             Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));

        if (!canEdit(item, currentUser)) {
            return "redirect:/items/" + id;
        }

        if (bindingResult.hasErrors()) {
            form.setId(id);
            form.setImagePath(item.getImagePath());
            model.addAttribute("item", form);
            return "items/form";
        }

        if (!imageFile.isEmpty()) {
            String imagePath = saveUploadedFile(imageFile);
            item.setImagePath(imagePath);
        }

        item.setTitle(form.getTitle());
        item.setDescription(form.getDescription());
        item.setCategory(form.getCategory());
        item.setLocation(form.getLocation());
        item.setFoundDate(form.getFoundDate());
        item.setPhoneNumber(form.getPhoneNumber());
        item.setStatus(form.getStatus());

        itemService.save(item);
        return "redirect:/items/" + id;
    }

    @PostMapping("/{id}/delete")
    public String deleteItem(@PathVariable Long id,
                             @AuthenticationPrincipal UserDetails currentUser) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found: " + id));
        if (!canEdit(item, currentUser)) {
            return "redirect:/items/" + id;
        }
        itemService.deleteById(id);
        return "redirect:/items";
    }

    private boolean canEdit(Item item, UserDetails currentUser) {
        if (currentUser == null) return false;
        boolean isOwner = item.getPostedBy() != null
                && item.getPostedBy().getUsername().equals(currentUser.getUsername());
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        return isOwner || isAdmin;
    }

    private String saveUploadedFile(MultipartFile file) {
        try {
            Path uploadPath = Paths.get(UPLOAD_DIRECTORY).toAbsolutePath().normalize();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(uniqueFilename);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + uniqueFilename;
        } catch (IOException e) {
            throw new RuntimeException("Failed to store uploaded file", e);
        }
    }
}