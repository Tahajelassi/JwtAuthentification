package com.app.jwtauthentication.domain;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static javax.persistence.FetchType.LAZY;

@Entity
public class Comment extends Identity {

    @OneToOne(fetch = LAZY)
    private User commentedBy;

    private String content;
    private LocalDateTime commentedAt = now();

    private Comment() {

    }

    public Comment(User commentedBy, String content) {
        this.commentedBy = commentedBy;
        this.content = content;
    }

    public User getCommentedBy() {
        return commentedBy;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }

    public Comment updateComment(String content) {
        Assert.notNull(content, "content cannot be null");
        this.content = content;
        this.commentedAt = now();
        return this;
    }
}
