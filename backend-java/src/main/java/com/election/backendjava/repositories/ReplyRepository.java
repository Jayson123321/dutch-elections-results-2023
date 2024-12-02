package com.election.backendjava.repositories;

import com.election.backendjava.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByUserForum_ForumId(Long forumId);
}