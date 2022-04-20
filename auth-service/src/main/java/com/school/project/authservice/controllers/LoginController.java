package com.school.project.authservice.controllers;

import com.school.project.authservice.models.requests.LoginRequest;
import com.school.project.authservice.models.responses.LoginResponse;
import com.school.project.authservice.services.CustomUserDetailsService;
import com.school.project.authservice.utils.JwtTokenUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.Objects;

@RequestMapping("/login")
@RestController
public class LoginController {
    private CustomUserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    public LoginController(CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.userDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    private boolean authenticate(String username, String password) {
        Objects.requireNonNull(username, "Username cannot be null");
        Objects.requireNonNull(password, "Password cannot be null");
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return true;
        } catch (DisabledException | BadCredentialsException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        boolean authStatus = authenticate(loginRequest.getEmailAddress(), loginRequest.getPassword());
        if(!authStatus) return new ResponseEntity<>(new LoginResponse("79", null, null), HttpStatus.BAD_REQUEST);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmailAddress());
        final String token = jwtTokenUtil.generateToken(userDetails);
        final Date tokenExpirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        return new ResponseEntity<>(new LoginResponse("99", token, tokenExpirationDate), HttpStatus.OK);
    }
}
