package com.election.backendjava.repositories;

import com.election.backendjava.entities.UserForum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostForumRepository extends JpaRepository<UserForum, Long> {

}
