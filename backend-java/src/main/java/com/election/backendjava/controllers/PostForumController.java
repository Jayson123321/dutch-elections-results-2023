package com.election.backendjava.controllers;

import com.election.backendjava.entities.Reply;
import com.election.backendjava.entities.User;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.services.ForumService;
import com.election.backendjava.services.UserService;
import com.election.backendjava.utils.JwtUtil;
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

        // Extract user ID from token
        String userId = jwtUtil.extractUserId(token.replace("Bearer ", ""));

        // Find the user by ID
        User user = userService.findById(Long.parseLong(userId));

        // Set the user to the forum
        userForum.setUser(user);

        // Save the forum
        UserForum savedForum = forumService.save(userForum);

        // Create a response with the username
        return ResponseEntity.status(HttpStatus.CREATED).body("Forum posted by: " + user.getUsername());
    }

    @GetMapping
    public ResponseEntity<Page<UserForum>> getAllForums(@RequestParam(value = "page", defaultValue = "0") int page) {
        Page<UserForum> forums = forumService.getAllForums(page);
        return ResponseEntity.ok(forums);
    }

    @PostMapping("/{forumId}/replies")
    public Reply saveReply(@PathVariable Long forumId, @RequestBody Reply reply) {
        UserForum userForum = forumService.getForumById(forumId);  // Retrieve the forum by ID
        reply.setUserForum(userForum);
        return forumService.saveReply(reply);
    }

    @GetMapping("/{forumId}/replies")
    public ResponseEntity<List<Reply>> getRepliesByForumId(@PathVariable Long forumId) {
        try {
            List<Reply> replies = forumService.getRepliesByForumId(forumId);
            return ResponseEntity.ok(replies);
        } catch (Exception e) {
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