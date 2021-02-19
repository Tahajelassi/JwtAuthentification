package com.app.jwtauthentication.domain;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Blog extends Identity {

    @ManyToOne(fetch = LAZY)
    private User createdBy;

    @OneToMany(fetch = LAZY)
    private List<ThumbsUp> thumbsUps = new ArrayList<>();

    @OneToMany(fetch = LAZY)
    private List<Comment> comments = new ArrayList<>();

    private String content;
    private LocalDateTime createdAt = now();


    private Blog() {

    }

    public Blog(String content, User createdBy) {
        this.content = content;
        this.createdBy = createdBy;
    }

    public String getContent() {
        return content;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public List<ThumbsUp> getThumbsUps() {
        return thumbsUps;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
