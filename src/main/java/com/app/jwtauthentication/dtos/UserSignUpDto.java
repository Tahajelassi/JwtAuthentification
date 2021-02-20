package com.app.jwtauthentication.dtos;

import com.app.jwtauthentication.domain.RoleName;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class UserSignUpDto extends UserCredentialsDto {

    @NotBlank
    @Size(min = 3, max = 50)
    private String firstname;

    @NotBlank
    @Size(min = 3, max = 50)
    private String lastname;

    @NotBlank
    @Size(max = 60)
    @Email
    private String email;

    private Set<RoleName> roles;

    @Size(max = 255)
    private String address;

    private UserSignUpDto() {
        super();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public Set<RoleName> getRoles() {
        return roles;
    }

    public String getAddress() {
        return address;
    }
}