package com.election.backendjava.controllers;

import com.election.backendjava.entities.UserForum;
import com.election.backendjava.repositories.PostForumRepository;
import com.election.backendjava.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersforum")
public class PostForumController {

    @Autowired
    private ForumService forumService;;

    // stuur de forums naar database
    @PostMapping
    public UserForum saveUserForum(@RequestBody UserForum userForum) {
        return forumService.save(userForum);
    }

    // Haal alle forums op
    @GetMapping
    public List<UserForum> getAllForums() {
        return forumService.getAllForums();
    }
}
