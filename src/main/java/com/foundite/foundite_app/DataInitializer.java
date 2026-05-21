package com.foundite.foundite_app;

import com.foundite.foundite_app.model.Item;
import com.foundite.foundite_app.model.User;
import com.foundite.foundite_app.repository.ItemRepository;
import com.foundite.foundite_app.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
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
                admin.setFullName("Administrator");
                admin.setRoles(Set.of("ROLE_ADMIN", "ROLE_USER"));
                userRepository.save(admin);
                System.out.println("✅ Admin créé : admin / admin123");
            }

            if (userRepository.findByUsername("hugo").isEmpty()) {
                User user = new User("hugo", "hugo@foundite.com",
                        passwordEncoder.encode("hugo123"), "Hugo Chi");
                userRepository.save(user);
                System.out.println("✅ User créé : hugo / hugo123");
            }

            if (itemRepository.count() == 0) {
                User admin = userRepository.findByUsername("admin").get();
                User hugo  = userRepository.findByUsername("hugo").get();

                createItem(itemRepository, "Black iPhone 14",
                        "Found near the cafeteria, cracked screen protector.",
                        "Electronics", "Cafeteria", LocalDate.of(2025, 4, 10),
                        Item.ItemStatus.FOUND, admin, "+33 6 11 22 33 44");

                createItem(itemRepository, "Blue Backpack",
                        "Navy blue backpack with a keychain, found on a bench.",
                        "Bags", "Main Campus Garden", LocalDate.of(2025, 4, 15),
                        Item.ItemStatus.FOUND, hugo, "+33 6 55 66 77 88");

                createItem(itemRepository, "Car Keys",
                        "Toyota keys with a red lanyard, lost in building A.",
                        "Keys", "Building A", LocalDate.of(2025, 4, 20),
                        Item.ItemStatus.LOST, hugo, "+33 6 55 66 77 88");

                createItem(itemRepository, "Student ID Card",
                        "Found on the floor near the printer room.",
                        "Documents", "Library 2nd Floor", LocalDate.of(2025, 4, 22),
                        Item.ItemStatus.FOUND, admin, "+33 6 11 22 33 44");

                createItem(itemRepository, "Silver Watch",
                        "Casio watch found in the gym locker room.",
                        "Jewelry", "Gymnasium", LocalDate.of(2025, 4, 25),
                        Item.ItemStatus.FOUND, hugo, "+33 6 55 66 77 88");

                createItem(itemRepository, "Black Hoodie",
                        "Size M, lost after the evening lecture.",
                        "Clothing", "Amphitheatre B", LocalDate.of(2025, 4, 28),
                        Item.ItemStatus.LOST, admin, "+33 7 99 88 77 66");

                System.out.println("✅ 6 items de test créés.");
            }
        };
    }

    private void createItem(ItemRepository repo, String title, String description,
                            String category, String location, LocalDate date,
                            Item.ItemStatus status, User postedBy, String phoneNumber) {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setCategory(category);
        item.setLocation(location);
        item.setFoundDate(date);
        item.setStatus(status);
        item.setPostedBy(postedBy);
        item.setPhoneNumber(phoneNumber);
        repo.save(item);
    }
}