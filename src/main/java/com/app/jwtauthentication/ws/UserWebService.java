package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.dtos.UserDto;
import com.app.jwtauthentication.services.UserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserWebService {

    private final UserService userService;

    public UserWebService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users")
    public UserDto loadUserByToken() {
        return this.userService.getUser();
    }
}
