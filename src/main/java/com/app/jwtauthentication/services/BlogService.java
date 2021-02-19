package com.app.jwtauthentication.services;

import com.app.jwtauthentication.domain.Blog;
import com.app.jwtauthentication.domain.User;
import com.app.jwtauthentication.dtos.BlogDto;
import com.app.jwtauthentication.repository.BlogRepository;
import com.app.jwtauthentication.repository.UserRepository;
import com.app.jwtauthentication.security.services.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogService(BlogRepository blogRepository, UserRepository userRepository) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public BlogDto saveBlog(BlogDto blogDto) {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User createdBy = userRepository.findById(userPrinciple.getId())
                .orElseThrow(RuntimeException::new);
        Blog blog = this.blogRepository.save(new Blog(blogDto.getContent(), createdBy));
        return new BlogDto(blog);
    }

    @Transactional(readOnly = true)
    public BlogDto getBlogById(String id) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Blog not found, %s", id)));
        return new BlogDto(blog);
    }

    @Transactional(readOnly = true)
    public List<BlogDto> getAllBlogs() {
        return this.blogRepository.findAll()
                .stream()
                .map(BlogDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void removeBlog(String id) {
        Blog blog = this.blogRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        if (!blog.getCreatedBy().getId().equals(userPrinciple.getId())) {
            throw new RuntimeException("can only delete your blog");
        }
        this.blogRepository.delete(blog);
    }
}
