package com.app.jwtauthentication.dtos;

import com.app.jwtauthentication.domain.ThumbsUp;

public class ThumbsUpDto {

    private UserDto likedBy;

    public ThumbsUpDto(ThumbsUp thumbsUp) {
        this.likedBy = new UserDto(thumbsUp.getLikedBy());
    }

    public UserDto getLikedBy() {
        return likedBy;
    }
}
