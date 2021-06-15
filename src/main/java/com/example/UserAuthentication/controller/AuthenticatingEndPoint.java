package com.example.UserAuthentication.controller;

import com.example.UserAuthentication.models.AuthenticationRequest;
import com.example.UserAuthentication.models.AuthenticationResponse;
import com.example.UserAuthentication.service.MyUserDetailsService;
import com.example.UserAuthentication.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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
        Authentication authenticate=null;
        try {
             authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUserName(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("incorrect credentials");
        }
        UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }




}
