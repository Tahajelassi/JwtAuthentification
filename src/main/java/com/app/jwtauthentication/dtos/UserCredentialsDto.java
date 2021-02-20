package com.app.jwtauthentication.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserCredentialsDto {

    @NotBlank
    @Size(min = 3, max = 60)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    protected UserCredentialsDto() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
