package com.app.jwtauthentication.domain;

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

    public User getCommentedBy() {
        return commentedBy;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCommentedAt() {
        return commentedAt;
    }
}
