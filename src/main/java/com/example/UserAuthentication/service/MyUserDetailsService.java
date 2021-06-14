package com.example.UserAuthentication.service;

import com.example.UserAuthentication.DAO.UserDetailsRepository;
import com.example.UserAuthentication.entity.UserDetailsImp;
import com.example.UserAuthentication.models.UserRole;
import com.example.UserAuthentication.models.dto.UserDto;
import com.example.UserAuthentication.models.dto.UserReturnedDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public MyUserDetailsService(UserDetailsRepository userDetailsRepository, ModelMapper modelMapper) {
        this.userDetailsRepository = userDetailsRepository;
        this.modelMapper = modelMapper;

    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    // must return UserDetails implementation, used by spring
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserDetails user = userDetailsRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(userName);
        }
        return user;
    }

    public List<UserReturnedDto> getAll() {
      return userDetailsRepository.findBy();
    }

    public UserReturnedDto getUser(long id) {
        return userDetailsRepository.findUserById(id);
    }

    public long saveUser(UserDto userDetails) {
        UserDetailsImp map = modelMapper.map(userDetails, UserDetailsImp.class);
        map.setUserRole(UserRole.USER_ROLE);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        UserDetailsImp save = userDetailsRepository.save(map);
        return save.getId();
    }
}

