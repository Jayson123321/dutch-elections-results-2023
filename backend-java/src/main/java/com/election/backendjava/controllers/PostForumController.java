package com.election.backendjava.controllers;

import com.election.backendjava.entities.Reply;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersforum")
public class PostForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping
    public UserForum saveUserForum(@RequestBody UserForum userForum) {
        return forumService.save(userForum);
    }

    @GetMapping
    public List<UserForum> getAllForums() {
        return forumService.getAllForums();
    }

    @PostMapping("/{forumId}/replies")
    public Reply saveReply(@PathVariable Long forumId, @RequestBody Reply reply) {
        UserForum userForum = forumService.getAllForums().stream()
                .filter(forum -> forum.getForumId().equals(forumId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Forum not found"));
        reply.setUserForum(userForum);
        return forumService.saveReply(reply);
    }

    @GetMapping("/{forumId}/replies")
    public List<Reply> getRepliesByForumId(@PathVariable Long forumId) {
        return forumService.getRepliesByForumId(forumId);
    }
}