// src/main/java/com/election/backendjava/repositories/AnswerRepository.java
package com.election.backendjava.repositories;

import com.election.backendjava.entities.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}