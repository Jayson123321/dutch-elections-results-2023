package com.election.backendjava.services;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String login(User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new Exception("Email cannot be null or empty.");
        }

        User existingUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new Exception("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
            throw new Exception("Invalid credentials");
        }

        return existingUser.getUsername();
    }

    public void register(User user) throws Exception {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new Exception("Email cannot be null or empty.");
        }

        if (!user.getEmail().contains("@")) {
            throw new Exception("Invalid email format.");
        }

        if (user.getPassword().length() < 8) {
            throw new Exception("Password must be at least 8 characters long.");
        }

        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}