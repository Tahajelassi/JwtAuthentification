package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.dtos.BlogDto;
import com.app.jwtauthentication.services.BlogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class BlogWebService {

    private final BlogService blogService;

    public BlogWebService(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/api/blog/")
    public BlogDto saveBlog(@RequestBody BlogDto blogDto) {
        return this.blogService.saveBlog(blogDto);
    }

    @GetMapping("/api/blog/{id}")
    public BlogDto getBlogById(@PathVariable String id) {
        return this.blogService.getBlogById(id);
    }

    @GetMapping("/api/blog/")
    public List<BlogDto> getAllBlogs() {
        return this.blogService.getAllBlogs();
    }

    @DeleteMapping("/api/blog/{id}")
    public ResponseEntity<String> deleteBlog(@PathVariable String id) {
        try {
            this.blogService.removeBlog(id);
            return ResponseEntity.ok("Blog deleted");
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }
}
