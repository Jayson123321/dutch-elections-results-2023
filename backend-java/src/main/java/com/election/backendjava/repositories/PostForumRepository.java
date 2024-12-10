package com.election.backendjava.repositories;

import com.election.backendjava.entities.UserForum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostForumRepository extends JpaRepository<UserForum, Long> {
    @Query("SELECT f FROM UserForum f ORDER BY f.createdAt DESC")
    List<UserForum> findAllSortedByCreatedAt();

}
