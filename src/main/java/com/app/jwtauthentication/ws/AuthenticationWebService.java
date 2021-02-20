package com.app.jwtauthentication.ws;

import com.app.jwtauthentication.dtos.JwtResponse;
import com.app.jwtauthentication.dtos.UserCredentialsDto;
import com.app.jwtauthentication.dtos.UserSignUpDto;
import com.app.jwtauthentication.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationWebService {

    private final AuthenticationService authenticationService;

    public AuthenticationWebService(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody UserCredentialsDto userCredentialsDto) {
        JwtResponse jwtResponse = this.authenticationService.authenticateUser(userCredentialsDto);
        return ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignUpDto userSignUpDto) {
        try {
            JwtResponse jwtResponse = this.authenticationService.registerUser(userSignUpDto);
            return new ResponseEntity<>(jwtResponse, CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), BAD_REQUEST);
        }
    }
}