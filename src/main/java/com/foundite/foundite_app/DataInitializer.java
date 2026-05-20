package com.foundite.foundite_app;

import com.foundite.foundite_app.model.Item;
import com.foundite.foundite_app.model.User;
import com.foundite.foundite_app.repository.ItemRepository;
import com.foundite.foundite_app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(ItemRepository itemRepository, 
                                      UserRepository userRepository, 
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@foundite.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of("ADMIN")); 
                userRepository.save(admin);
                System.out.println("Admin créé : admin / admin123");
            }

            if (itemRepository.count() == 0) {
                Item item = new Item();
                item.setTitle("Black Wallet");
                item.setDescription("Found near the library.");
                item.setCategory("Personal");
                item.setLocation("Library Entrance");
                item.setStatus(Item.ItemStatus.FOUND);
                itemRepository.save(item);
                System.out.println("Item de test créé.");
            }
        };
    }
}