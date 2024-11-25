// src/main/java/com/election/backendjava/entities/Answer.java
package com.election.backendjava.entities;

import jakarta.persistence.*;

@Entity
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private UserForum question;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserForum getQuestion() {
        return question;
    }

    public void setQuestion(UserForum question) {
        this.question = question;
    }
}