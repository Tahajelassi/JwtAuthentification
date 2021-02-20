package com.app.jwtauthentication.services;

import com.app.jwtauthentication.domain.Blog;
import com.app.jwtauthentication.domain.ThumbsUp;
import com.app.jwtauthentication.domain.User;
import com.app.jwtauthentication.dtos.ThumbsUpDto;
import com.app.jwtauthentication.repository.BlogRepository;
import com.app.jwtauthentication.repository.ThumbsUpRepository;
import com.app.jwtauthentication.repository.UserRepository;
import com.app.jwtauthentication.security.services.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ThumbsUpService {

    private final ThumbsUpRepository thumbsUpRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public ThumbsUpService(ThumbsUpRepository thumbsUpRepository,
                           BlogRepository blogRepository,
                           UserRepository userRepository) {
        this.thumbsUpRepository = thumbsUpRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ThumbsUpDto likeBlog(String blogId) {
        Blog blog = this.blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException(String.format("blog not found %s", blogId)));

        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();

        User user = this.userRepository.findById(userPrinciple.getId())
                .orElseThrow(() -> new RuntimeException(String.format("user not found %s", userPrinciple.getId())));

        boolean userAlreadyLikedBlog = blog.getThumbsUps().stream().anyMatch(thumbsUp -> thumbsUp.getLikedBy().getId().equals(user.getId()));
        if (userAlreadyLikedBlog) {
            throw new IllegalStateException(String.format("User already liked blog %s", blog.getId()));
        }
        ThumbsUp savedThumbsUp = new ThumbsUp(user);
        blog.addThumbsUp(savedThumbsUp);
        return new ThumbsUpDto(savedThumbsUp);
    }

    @Transactional
    public ThumbsUpDto dislikeBlog(String blogId, String thumbsUpId) {
        Blog blog = this.blogRepository.findById(blogId)
                .orElseThrow(() -> new RuntimeException(String.format("blog not found %s", blogId)));

        ThumbsUp thumbsUp = this.thumbsUpRepository.findById(thumbsUpId)
                .orElseThrow(() -> new RuntimeException(String.format("thumbsUp not found %s", thumbsUpId)));

        blog.removeThumbsUp(thumbsUp);
        return null;
    }
}
