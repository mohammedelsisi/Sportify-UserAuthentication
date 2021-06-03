package com.example.UserAuthentication.controller;

import com.example.UserAuthentication.models.AuthenticationRequest;
import com.example.UserAuthentication.models.AuthenticationResponse;
import com.example.UserAuthentication.service.MyUserDetailsService;
import com.example.UserAuthentication.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticatingEndPoint {
    final AuthenticationManager authenticationManager;
    final MyUserDetailsService userDetailsService;
    final JwtUtil jwtUtil;

    public AuthenticatingEndPoint(JwtUtil jwtUtil, AuthenticationManager authenticationManager, MyUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> getAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect credentials");
        }
        var userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }



}
