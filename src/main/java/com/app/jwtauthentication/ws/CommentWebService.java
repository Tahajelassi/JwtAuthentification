package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.dtos.CommentDto;
import com.app.jwtauthentication.services.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentWebService {

    private final CommentService commentService;

    public CommentWebService(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/api/comments/blogs/{blogId}")
    public CommentDto addCommentToBlog(@PathVariable String blogId, @RequestBody CommentDto commentDto) {
        return this.commentService.addComment(blogId, commentDto);
    }

    @PutMapping("/api/comments/{id}")
    public CommentDto updateComment(@PathVariable String id, @RequestBody CommentDto commentDto) {
        return this.commentService.updateComment(id, commentDto);
    }

    @DeleteMapping("/api/comments/{id}/blogs/{blogId}")
    public ResponseEntity<String> deleteComment(@PathVariable String id, @PathVariable String blogId) {
        try {
            this.commentService.deleteComment(id, blogId);
            return ResponseEntity.ok("Comment deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
