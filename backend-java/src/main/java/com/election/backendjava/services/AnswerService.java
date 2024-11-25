// src/main/java/com/election/backendjava/services/AnswerService.java
package com.election.backendjava.services;

import com.election.backendjava.entities.Answer;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.repositories.AnswerRepository;
import com.election.backendjava.repositories.PostForumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private PostForumRepository postForumRepository;

    public List<Answer> getAllAnswers() {
        return answerRepository.findAll();
    }

    public Answer getAnswerById(Long id) {
        return answerRepository.findById(id).orElse(null);
    }

    public Answer saveAnswer(Long questionId, Answer answer) {
        UserForum question = postForumRepository.findById(questionId).orElse(null);
        if (question != null) {
            answer.setQuestion(question);
            return answerRepository.save(answer);
        }
        return null;
    }
}