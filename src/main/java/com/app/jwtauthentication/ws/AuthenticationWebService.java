package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.domain.Role;
import com.app.jwtauthentication.domain.RoleName;
import com.app.jwtauthentication.domain.User;
import com.app.jwtauthentication.dtos.JwtResponse;
import com.app.jwtauthentication.dtos.LoginDto;
import com.app.jwtauthentication.dtos.SignUpDto;
import com.app.jwtauthentication.repository.RoleRepository;
import com.app.jwtauthentication.repository.UserRepository;
import com.app.jwtauthentication.security.jwt.JwtProvider;
import com.app.jwtauthentication.security.services.UserPrinciple;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationWebService {


    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;


    private JwtProvider jwtProvider;

    public AuthenticationWebService(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateJwtToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();

        return ok(new JwtResponse(jwt,
                userPrinciple.getUsername(),
                userPrinciple.getAuthorities(),
                userPrinciple.getId()));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpDto signUpRequest) {
        if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
            return new ResponseEntity<>("username exists", FOUND);
        }
        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            return new ResponseEntity<>("Email exists", FOUND);
        }

        // Creating user's account
        User user = new User(signUpRequest.getFirstname(), signUpRequest.getLastname(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()), signUpRequest.getAddress());

        Set<Role> roles = associateRolesToUser(signUpRequest.getRole());
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully!", CREATED);
    }

    private Set<Role> associateRolesToUser(Set<String> roles) {
        Set<Role> setOfRoles = new HashSet<>();

        roles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Admin Role not found."));
                    setOfRoles.add(adminRole);

                    break;
                case "pm":
                    Role pmRole = roleRepository.findByName(RoleName.ROLE_PM)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: Pm Role not found."));
                    setOfRoles.add(pmRole);

                    break;
                case "user":
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
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