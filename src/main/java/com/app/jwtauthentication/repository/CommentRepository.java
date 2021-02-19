package com.app.jwtauthentication.repository;

import com.app.jwtauthentication.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {

}
