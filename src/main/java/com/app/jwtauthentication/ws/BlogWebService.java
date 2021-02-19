package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.domain.Blog;
import com.app.jwtauthentication.domain.User;
import com.app.jwtauthentication.dtos.BlogDto;
import com.app.jwtauthentication.repository.BlogRepository;
import com.app.jwtauthentication.repository.UserRepository;
import com.app.jwtauthentication.security.services.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController("/api/blog")
public class BlogWebService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogWebService(BlogRepository blogRepository,
                          UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    @Transactional
    public BlogDto saveBlog(@RequestBody BlogDto blogDto) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User createdBy = userRepository.findById(userPrinciple.getId())
                .orElseThrow(RuntimeException::new);
        Blog blog = this.blogRepository.save(new Blog(blogDto.getContent(), createdBy));
        return new BlogDto(blog);
    }

    @GetMapping({"id"})
    public BlogDto getBlogById(@PathVariable String id) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Blog not found, %s", id)));
        return new BlogDto(blog);
    }

    @GetMapping
    public List<BlogDto> getAllBlogs() {
        return this.blogRepository.findAll()
                .stream()
                .map(BlogDto::new)
                .collect(Collectors.toList());
    }
}
