//package com.election.backendjava.services;
//
//import com.election.backendjava.entities.Reply;
//import com.election.backendjava.entities.UserForum;
//import com.election.backendjava.repositories.PostForumRepository;
//import com.election.backendjava.repositories.ReplyRepository;
//import jakarta.persistence.EntityNotFoundException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ForumService {
//    private static final Logger logger = LoggerFactory.getLogger(ForumService.class);
//
//    @Autowired
//    private PostForumRepository postForumRepository;
//
//    @Autowired
//    private ReplyRepository replyRepository;
//
//    public UserForum save(UserForum form) {
//        return postForumRepository.save(form);
//    }
//
//    public Page<UserForum> getAllForums(int page) {
//        Pageable pageable = PageRequest.of( page, 5);
//        Page<UserForum> forums = postForumRepository.findAll(pageable);
//        logger.info("Aantal opgehaalde forums op deze pagina: {}", forums.getNumberOfElements());
//        return forums;
//    }
//
//    public Reply saveReply(Reply reply) {
//        return replyRepository.save(reply);
//    }
//
//    public List<Reply> getRepliesByForumId(Long forumId) {
//        return replyRepository.findByUserForum_ForumId(forumId);
//    }
//
//    public void deleteForumById(Long forumId) {
//        if (!postForumRepository.existsById(forumId)) {
//            throw new EntityNotFoundException("Forum met ID " + forumId + " bestaat niet.");
//        }
//
//        replyRepository.deleteByUserForum_ForumId(forumId);
//
//        postForumRepository.deleteById(forumId);
//
//        logger.info("Forum with ID {} and its replies have been deleted.", forumId);
//    }
//}

package com.election.backendjava.services;

import com.election.backendjava.entities.Reply;
import com.election.backendjava.entities.User;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.repositories.PostForumRepository;
import com.election.backendjava.repositories.ReplyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ForumService {
    private static final Logger logger = LoggerFactory.getLogger(ForumService.class);

    @Autowired
    private PostForumRepository postForumRepository;

    @Autowired
    private ReplyRepository replyRepository;

    public UserForum save(UserForum form) {
        return postForumRepository.save(form);
    }

    public Page<UserForum> getAllForums(int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<UserForum> forums = postForumRepository.findAll(pageable);
        logger.info("Aantal opgehaalde forums op deze pagina: {}", forums.getNumberOfElements());
        return forums;
    }

    public Reply saveReply(Reply reply) {
        return replyRepository.save(reply);
    }

    public List<Reply> getRepliesByForumId(Long forumId) {
        return replyRepository.findByUserForum_ForumId(forumId);
    }

    public UserForum getForumById(Long forumId) {
        Optional<UserForum> forum = postForumRepository.findById(forumId);
        if (forum.isPresent()) {
            return forum.get();
        } else {
            throw new EntityNotFoundException("Forum met ID " + forumId + " bestaat niet.");
        }
    }

    public void deleteForumById(Long forumId) {
        if (!postForumRepository.existsById(forumId)) {
            throw new EntityNotFoundException("Forum met ID " + forumId + " bestaat niet.");
        }
        replyRepository.deleteByUserForum_ForumId(forumId);
        postForumRepository.deleteById(forumId);
        logger.info("Forum with ID {} and its replies have been deleted.", forumId);
    }

    public Page<UserForum> getForumsByUserId(Long userId, int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<UserForum> forums = postForumRepository.findByUserId(userId, pageable);
        forums.forEach(forum -> forum.getReplies().size()); // Load replies
        return forums;
    }

}
