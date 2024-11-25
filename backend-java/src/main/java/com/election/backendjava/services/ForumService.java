package com.election.backendjava.services;

import com.election.backendjava.entities.UserForum;
import com.election.backendjava.repositories.PostForumRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ForumService {
    private static final Logger logger = LoggerFactory.getLogger(ForumService.class);

    @Autowired
    private PostForumRepository postForumRepository;

    public UserForum save(UserForum form) {
        return  postForumRepository.save(form);
    }

    public List<UserForum> getAllForums() {
//        return postForumRepository.findAll();
        List<UserForum> forums = postForumRepository.findAll();
        logger.info("Aantal opgehaalde forums: {}", forums.size());
        return forums;
    }
}
