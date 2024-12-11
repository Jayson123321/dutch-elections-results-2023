package com.election.backendjava.controllers;

import com.election.backendjava.entities.Reply;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.services.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<List<Reply>> getRepliesByForumId(@PathVariable Long forumId) {
        try {
            List<Reply> replies = forumService.getRepliesByForumId(forumId);
            return ResponseEntity.ok(replies);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<Void> deleteForum(@PathVariable Long forumId) {
        try {
            forumService.deleteForumById(forumId);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}