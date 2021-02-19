package com.app.jwtauthentication.services;

import com.app.jwtauthentication.dtos.UserDto;
import com.app.jwtauthentication.repository.UserRepository;
import com.app.jwtauthentication.security.services.UserPrinciple;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDto getUser() {
        UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
        return new UserDto(userPrinciple);
    }
}
