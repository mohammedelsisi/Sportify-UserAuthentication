package com.example.UserAuthentication.service;

import com.example.UserAuthentication.DAO.UserDetailsRepository;
import com.example.UserAuthentication.entity.UserDetailsImp;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    public MyUserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        System.out.println(userName);
        UserDetails user = userDetailsRepository.findByUserName(userName);
        System.out.println(user);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return user;
    }

}

