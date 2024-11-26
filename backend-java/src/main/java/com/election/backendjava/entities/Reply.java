package com.election.backendjava.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "forum_replies")
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id", nullable = false, updatable = false)
    private Long replyId;

    @Column(name = "reply_text", nullable = false)
    private String replyText;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "forum_id", nullable = false)
    private UserForum userForum;

    // Getters and setters
    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserForum getUserForum() {
        return userForum;
    }

    public void setUserForum(UserForum userForum) {
        this.userForum = userForum;
    }
}