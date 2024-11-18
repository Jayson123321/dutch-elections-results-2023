package com.election.backendjava.controllers;

import com.election.backendjava.entities.UserForum;
import com.election.backendjava.repositories.PostForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usersforum")
public class PostForumController {

    @Autowired
    private PostForumRepository postForumRepository;

    @PostMapping
    public UserForum saveUserForum(@RequestBody UserForum userForum) {
        return postForumRepository.save(userForum);
    }

}
