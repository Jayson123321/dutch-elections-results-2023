package com.election.backendjava.entities;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserForum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "forum_id", nullable = false, updatable = false)
    private Long forumId;

   @Column(name = "title", nullable = false, updatable = false)
   private String title;

   @Column(name = "description", nullable = false, updatable = false)
   private String description;

   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id" , nullable = false)
   @JsonBackReference
   private User user;

    @OneToMany(mappedBy = "userForum", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Reply> replies;

    //getters and setters
    public Long getForumId() {
        return forumId;
    }
    public void setForumId(Long forumId) {
        this.forumId = forumId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

}

