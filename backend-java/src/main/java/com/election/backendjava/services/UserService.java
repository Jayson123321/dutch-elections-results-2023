package com.election.backendjava.services;

import com.election.backendjava.entities.User;
import com.election.backendjava.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public long getUserCount() {
        return userRepository.count();
    }

    public void updateUser(Long id, User user) {
        System.out.println("Received PUT request to update user with ID: " + id); // debug
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUsername(user.getUsername()); // this is for the username only
            userToUpdate.setRole(user.getRole()); // updating role
            userRepository.save(userToUpdate);
        }
    }

    public void updateUserEmail(Long id, User user) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setEmail(user.getEmail()); // update email
            userRepository.save(userToUpdate);
        }
    }

    public void banUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToBan = existingUser.get();
            userToBan.setRole("banned");
            userRepository.save(userToBan);
        }
    }

    public void unbanUser(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUnban = existingUser.get();
            userToUnban.setRole("user");
            userRepository.save(userToUnban);
        }
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}