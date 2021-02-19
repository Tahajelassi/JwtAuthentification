package com.app.jwtauthentication.dtos;

import com.app.jwtauthentication.domain.User;

public class UserDto {

    private String id;
    private String firstname;
    private String lastname;
    private String address;
    private String email;


    public UserDto(User user) {
        this.id = user.getId();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.address = user.getAddress();
        this.email = user.getEmail();
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }
}
