package com.election.backendjava.service;

import com.election.backendjava.entities.Reply;
import com.election.backendjava.entities.UserForum;
import com.election.backendjava.repositories.PostForumRepository;
import com.election.backendjava.repositories.ReplyRepository;
import com.election.backendjava.services.ForumService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ForumServiceTest {

    @InjectMocks
    private ForumService forumService;

    @Mock
    private PostForumRepository postForumRepository;

    @Mock
    private ReplyRepository replyRepository;

    private UserForum forum;
    private Reply reply;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        forum = new UserForum();
        forum.setForumId(1L);
        forum.setTitle("Test Forum");

        reply = new Reply();
        reply.setReplyId(1L);
        reply.setReplyText("Test Reply");
        reply.setUserForum(forum);
    }

    @Test
    public void testSaveForum() {
        when(postForumRepository.save(forum)).thenReturn(forum);

        UserForum savedForum = forumService.save(forum);

        assertNotNull(savedForum);
        assertEquals(forum.getTitle(), savedForum.getTitle());
    }

    @Test
    public void testGetAllForums() {
        Pageable pageable = PageRequest.of(0, 5);
        List<UserForum> forumsList = new ArrayList<>();
        forumsList.add(forum);

        Page<UserForum> page = mock(Page.class);
        when(page.getContent()).thenReturn(forumsList);
        when(postForumRepository.findAll(pageable)).thenReturn(page);

        Page<UserForum> result = forumService.getAllForums(0);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    public void testSaveReply() {
        when(replyRepository.save(reply)).thenReturn(reply);

        Reply savedReply = forumService.saveReply(reply);

        assertNotNull(savedReply);
        assertEquals(reply.getReplyText(), savedReply.getReplyText());
    }

    @Test
    public void testGetRepliesByForumId() {
        List<Reply> replies = new ArrayList<>();
        replies.add(reply);

        when(replyRepository.findByUserForum_ForumId(1L)).thenReturn(replies);

        List<Reply> result = forumService.getRepliesByForumId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteForumById() {
        when(postForumRepository.existsById(1L)).thenReturn(true);
        doNothing().when(replyRepository).deleteByUserForum_ForumId(1L);
        doNothing().when(postForumRepository).deleteById(1L);

        forumService.deleteForumById(1L);

        verify(postForumRepository, times(1)).deleteById(1L);
        verify(replyRepository, times(1)).deleteByUserForum_ForumId(1L);
    }

    @Test
    public void testDeleteForumByIdThrowsException() {
        when(postForumRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> forumService.deleteForumById(1L));
    }
}

