// src/main/java/com/election/backendjava/controllers/AnswerController.java
package com.election.backendjava.controllers;

import com.election.backendjava.entities.Answer;
import com.election.backendjava.services.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {
    @Autowired
    private AnswerService answerService;

    @GetMapping
    public List<Answer> getAllAnswers() {
        return answerService.getAllAnswers();
    }

    @GetMapping("/{id}")
    public Answer getAnswerById(@PathVariable Long id) {
        return answerService.getAnswerById(id);
    }

    @PostMapping("/{questionId}")
    public Answer createAnswer(@PathVariable Long questionId, @RequestBody Answer answer) {
        return answerService.saveAnswer(questionId, answer);
    }
}