package com.foundite.config;

import com.foundite.model.Item;
import com.foundite.repository.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(ItemRepository itemRepository) {
        return args -> {
            if (itemRepository.count() == 0) {
                itemRepository.save(new Item(
                        "Black wallet",
                        "Leather wallet found near the cafeteria. Contains ID cards.",
                        "Accessories",
                        "Building A - Cafeteria"
                ));
                itemRepository.save(new Item(
                        "Blue umbrella",
                        "Forgotten in the lecture hall after the Software Engineering class.",
                        "Other",
                        "Room 204"
                ));
                itemRepository.save(new Item(
                        "USB stick 32GB",
                        "SanDisk USB stick, red color. Found in the library.",
                        "Electronics",
                        "Library - 2nd floor"
                ));
            }
        };
    }
}