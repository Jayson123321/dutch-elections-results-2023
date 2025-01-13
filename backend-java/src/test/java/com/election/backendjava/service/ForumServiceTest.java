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

    @InjectMocks  // ForumService wordt geïnjecteerd in de testklasse
    private ForumService forumService;

    @Mock  // Mock van het PostForumRepository, zodat we geen echte database nodig hebben
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
    }

    @Test
    public void testSaveForum() {
        // Wanneer de save methode wordt aangeroepen op het repository, geef het forum terug
        when(postForumRepository.save(forum)).thenReturn(forum);

        // Roep de service aan om het forum op te slaan
        UserForum savedForum = forumService.save(forum);


        // Controleer of het forum is opgeslagen
        assertNotNull(savedForum);
        assertEquals(forum.getTitle(), savedForum.getTitle());
    }

    @Test
    public void testGetAllForums() {
        Pageable pageable = PageRequest.of(0, 5); // Maak een pagina-aanvraag met 5 items per pagina
        List<UserForum> forumsList = new ArrayList<>();
        forumsList.add(forum);

        // Mock de Page en stel in wat de inhoud van de pagina moet zijn
        Page<UserForum> page = mock(Page.class);
        when(page.getContent()).thenReturn(forumsList);
        when(postForumRepository.findAll(pageable)).thenReturn(page);

        // Roep de service aan om alle forums op te halen

        Page<UserForum> result = forumService.getAllForums(0);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
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

