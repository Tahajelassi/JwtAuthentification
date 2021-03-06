package com.app.jwtauthentication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private String username;
    private String id;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String accessToken, String username,
                       Collection<? extends GrantedAuthority> authorities, String id) {
        this.token = accessToken;
        this.username = username;
        this.authorities = authorities;
        this.id = id;
    }

}