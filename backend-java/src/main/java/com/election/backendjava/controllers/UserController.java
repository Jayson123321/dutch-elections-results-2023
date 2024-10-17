package com.election.backendjava.controllers;

import com.election.backendjava.entities.User;
import com.election.backendjava.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public List<String> getAllUsernames() {
        return userService.getAllUsers().stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
    }
}
