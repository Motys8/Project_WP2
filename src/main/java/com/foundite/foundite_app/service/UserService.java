package com.foundite.foundite_app.service;



import com.foundite.foundite_app.model.User;

import com.foundite.foundite_app.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Service;



@Service

public class UserService {



private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;



public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;

        this.passwordEncoder = passwordEncoder;

    }



public User registerNewUser(String username, String email, String rawPassword, String fullName) {

        if (userRepository.existsByUsername(username)) {

            throw new IllegalArgumentException("Username already taken");

        }

        if (userRepository.existsByEmail(email)) {

            throw new IllegalArgumentException("Email already registered");

        }




        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(username, email, hashedPassword, fullName);

        return userRepository.save(user);

    }



public User findByUsername(String username) {

        return userRepository.findByUsername(username)

                .orElseThrow(() -> new IllegalArgumentException("User not found"));

    }

}