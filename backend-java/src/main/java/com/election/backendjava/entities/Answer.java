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
}