package com.app.jwtauthentication.dtos;

import com.app.jwtauthentication.domain.Blog;

import java.util.List;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.util.stream.Collectors.toList;

public class BlogDto {

    private String id;
    private String content;
    private UserDto createdBy;
    private String createdAt;
    private List<ThumbsUpDto> likes;
    private List<CommentDto> comments;

    public BlogDto(Blog blog) {
        this.id = blog.getId();
        this.content = blog.getContent();
        this.createdBy = new UserDto(blog.getCreatedBy());
        this.likes = blog.getThumbsUps().stream().map(ThumbsUpDto::new).collect(toList());
        this.comments = blog.getComments().stream().map(CommentDto::new).collect(toList());
        this.createdAt = blog.getCreatedAt().format(ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    BlogDto() {

    }

    public String getContent() {
        return content;
    }

    public UserDto getCreatedBy() {
        return createdBy;
    }

    public List<ThumbsUpDto> getLikes() {
        return likes;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public List<CommentDto> getComments() {
        return comments;
    }
}
