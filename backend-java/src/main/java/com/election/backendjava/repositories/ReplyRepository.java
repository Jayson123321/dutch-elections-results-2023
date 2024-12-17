package com.election.backendjava.repositories;

import com.election.backendjava.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByUserForum_ForumId(Long forumId);

    @Transactional
    @Modifying
    @Query("DELETE FROM Reply r WHERE r.userForum.forumId = :forumId")
    void deleteByUserForum_ForumId(@Param("forumId") Long forumId);
}