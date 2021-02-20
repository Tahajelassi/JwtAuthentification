package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.dtos.ThumbsUpDto;
import com.app.jwtauthentication.services.ThumbsUpService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThumbsUpWebService {

    private final ThumbsUpService thumbsUpService;

    public ThumbsUpWebService(ThumbsUpService thumbsUpService) {
        this.thumbsUpService = thumbsUpService;
    }

    @PostMapping("/api/thumbsUps/blogs/{blogId}")
    public ThumbsUpDto likeBlog(@PathVariable String blogId) {
        return this.thumbsUpService.likeBlog(blogId);
    }

    @DeleteMapping("/api/thumbsUps/{thumbsUpId}/blogs/{blogId}")
    public ThumbsUpDto dislikeBlog(@PathVariable String thumbsUpId,
                                   @PathVariable String blogId) {
        return this.thumbsUpService.dislikeBlog(blogId, thumbsUpId);
    }
}
