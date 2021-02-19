package com.app.jwtauthentication.services;

import com.app.jwtauthentication.domain.Role;
import com.app.jwtauthentication.domain.RoleName;
import com.app.jwtauthentication.domain.User;
import com.app.jwtauthentication.dtos.JwtResponse;
import com.app.jwtauthentication.dtos.LoginDto;
import com.app.jwtauthentication.dtos.SignUpDto;
import com.app.jwtauthentication.dtos.UserDto;
import com.app.jwtauthentication.repository.RoleRepository;
import com.app.jwtauthentication.repository.UserRepository;
import com.app.jwtauthentication.security.jwt.JwtProvider;
import com.app.jwtauthentication.security.services.UserPrinciple;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static com.app.jwtauthentication.domain.RoleName.ROLE_ADMIN;
import static com.app.jwtauthentication.domain.RoleName.ROLE_USER;
import static java.lang.String.format;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtProvider jwtProvider;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 RoleRepository roleRepository,
                                 PasswordEncoder encoder,
                                 JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    public JwtResponse authenticateUser(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return new JwtResponse(jwt,
                userPrinciple.getUsername(),
                userPrinciple.getAuthorities(),
                userPrinciple.getId());
    }

    @Transactional
    public UserDto registerUser(SignUpDto signUpDto) {
        if (userRepository.findByUsername(signUpDto.getUsername()).isPresent()) {
            throw new RuntimeException(format("user with username %s already exists", signUpDto.getUsername()));
        }
        if (userRepository.findByEmail(signUpDto.getEmail()).isPresent()) {
            throw new RuntimeException(format("user with email %s already exists", signUpDto.getEmail()));
        }

        User user = new User(
                signUpDto.getFirstname(),
                signUpDto.getLastname(),
                signUpDto.getUsername(),
                signUpDto.getEmail(),
                encoder.encode(signUpDto.getPassword()),
                signUpDto.getAddress()
        );

        user.setRoles(associateRolesToUser(signUpDto.getRoles()));
        User savedUser = userRepository.save(user);
        return new UserDto(savedUser);
    }

    private Set<Role> associateRolesToUser(Set<RoleName> roles) {
        Set<Role> setOfRoles = new HashSet<>();

        roles.forEach(role -> {
            switch (role) {
                case ROLE_ADMIN:
                    Role adminRole = roleRepository.findByName(ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not found."));
                    setOfRoles.add(adminRole);

                    break;
                case ROLE_USER:
                    Role userRole = roleRepository.findByName(ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
                    setOfRoles.add(userRole);
                    break;
                default:
                    throw new RuntimeException("Fail ! -> Cause : the type" + role + "is not valid");
            }
        });
        return setOfRoles;
    }
}
