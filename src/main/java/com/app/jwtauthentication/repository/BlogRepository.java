package com.app.jwtauthentication.repository;

import com.app.jwtauthentication.domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Blog, String> {

}
