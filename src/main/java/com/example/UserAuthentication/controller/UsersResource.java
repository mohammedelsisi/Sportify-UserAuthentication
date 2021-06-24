package com.example.UserAuthentication.controller;

import com.example.UserAuthentication.models.dto.UserDto;
import com.example.UserAuthentication.models.dto.UserReturnedDto;
import com.example.UserAuthentication.service.MyUserDetailsService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersResource {


    final MyUserDetailsService myUserDetailsService;

    public UsersResource(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }


    @PostMapping
    public ResponseEntity<Object> saveSelected(@RequestBody UserDto userDetails, HttpServletRequest request) {
        long id = myUserDetailsService.saveUserAndCreateUserScore(userDetails);
        var httpHeaders = new HttpHeaders();
        httpHeaders.add("location", request.getRequestURL() + "/" + id);
        return ResponseEntity.status(HttpStatus.CREATED).headers(httpHeaders).build();
    }

    @GetMapping("/{id}")
    public UserReturnedDto getSelection(@PathVariable long id) {
        return myUserDetailsService.getUser(id);

    }

    @GetMapping
    public ResponseEntity<List<UserReturnedDto>> getAllSelection() {
        List<UserReturnedDto> all = myUserDetailsService.getAll();
        return ResponseEntity.ok(all);
    }
}
