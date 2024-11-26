package com.election.backendjava.controllers;

import com.election.backendjava.entities.User;
import com.election.backendjava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin // toegevoegd door mij (achraf) om te kijken of het mijn probleem oplost
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @GetMapping("/count")
    public long getUserCount() {
        return userService.getUserCount();
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    @PutMapping("/{id}/email")
    public void updateUserEmail(@PathVariable Long id, @RequestBody User user) {
        userService.updateUserEmail(id, user);
    }

    @PutMapping("/{id}/ban")
    public void banUser(@PathVariable Long id) {
        userService.banUser(id);
    }
}
