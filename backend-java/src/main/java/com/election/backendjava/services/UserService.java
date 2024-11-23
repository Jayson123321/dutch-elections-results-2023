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
        System.out.println("Received PUT request to update user with ID: " + id); //debug
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User userToUpdate = existingUser.get();
            userToUpdate.setUsername(user.getUsername()); // this is for the username only
            userRepository.save(userToUpdate);
        }
    }
}
