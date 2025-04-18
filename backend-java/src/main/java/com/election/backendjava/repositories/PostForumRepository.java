package com.election.backendjava.repositories;

import com.election.backendjava.entities.UserForum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostForumRepository extends JpaRepository<UserForum, Long> {
    Page<UserForum> findAll(Pageable pageable);

    Page<UserForum> findByUserId(Long userId, Pageable pageable);
}
