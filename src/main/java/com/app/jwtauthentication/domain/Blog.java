package com.app.jwtauthentication.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;
import static org.springframework.util.Assert.notNull;

@Entity
public class Blog extends Identity {

    @ManyToOne(fetch = LAZY)
    private User createdBy;

    @OneToMany(fetch = LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ThumbsUp> thumbsUps = new ArrayList<>();

    @OneToMany(fetch = LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
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

    public void addComment(Comment comment) {
        notNull(comment, "comment cannot be null");
        this.comments.add(comment);
    }

    public void removeComment(Comment comment) {
        notNull(comment, "comment cannot be null");
        this.comments.remove(comment);
    }

    public void addThumbsUp(ThumbsUp thumbsUp) {
        notNull(thumbsUp, "thumbsUp cannot be null");
        this.thumbsUps.add(thumbsUp);
    }

    public void removeThumbsUp(ThumbsUp thumbsUp) {
        notNull(thumbsUp, "thumbsUp cannot be null");
        this.thumbsUps.remove(thumbsUp);
    }
}
