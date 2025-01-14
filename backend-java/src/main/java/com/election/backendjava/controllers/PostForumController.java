package com.election.backendjava.controllers;

import com.election.backendjava.entities.Reply;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.services.ForumService;
import com.election.backendjava.services.UserService;
import com.election.backendjava.utils.JwtUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersforum")
public class PostForumController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> saveUserForum(@RequestBody UserForum userForum, @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to post a forum.");
        }

        try {
            UserForum savedForum = forumService.saveUserForum(userForum, token);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedForum);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the forum.");
        }
    }

    @GetMapping
    public ResponseEntity<Page<UserForum>> getAllForums(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<UserForum> forums = forumService.getAllForums(page);
        return ResponseEntity.ok(forums);
    }

    @PostMapping("/{forumId}/replies")
    public ResponseEntity<?> saveReply(@PathVariable Long forumId, @RequestBody Reply reply) {
        try {
            Reply savedReply = forumService.saveReplyToForum(forumId, reply);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReply);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Forum not found.");
        }
    }

    @GetMapping("/{forumId}/replies")
    public ResponseEntity<List<Reply>> getRepliesByForumId(@PathVariable Long forumId) {
        try {
            List<Reply> replies = forumService.getRepliesByForumId(forumId);
            return ResponseEntity.ok(replies);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{forumId}")
    public ResponseEntity<String> deleteForum(@PathVariable Long forumId, @RequestHeader(value = "Authorization", required = false) String token) {
        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You must be logged in to delete a forum.");
        }

        try {
            forumService.deleteForum(forumId, token);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Forum not found.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to delete this forum.");
        }
    }

    @GetMapping("/api/user")
    public ResponseEntity<Page<UserForum>> getUserForums(
            @RequestHeader(value = "Authorization", required = false) String token,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        if (token == null || !jwtUtil.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Page<UserForum> forums = forumService.getUserForums(token, page);
        return ResponseEntity.ok(forums);
    }
}
