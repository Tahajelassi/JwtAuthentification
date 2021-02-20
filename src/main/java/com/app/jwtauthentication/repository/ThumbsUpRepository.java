package com.app.jwtauthentication.repository;

import com.app.jwtauthentication.domain.ThumbsUp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThumbsUpRepository extends JpaRepository<ThumbsUp, String> {

}
