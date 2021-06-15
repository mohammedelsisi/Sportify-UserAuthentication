package com.example.UserAuthentication.service;
import  com.example.UserAuthentication.models.entity.UserDetailsImp;
import com.example.UserAuthentication.DAO.UserDetailsRepository;
import com.example.UserAuthentication.models.UserRole;
import com.example.UserAuthentication.models.dto.NewUserMessage;
import com.example.UserAuthentication.models.dto.UserDto;
import com.example.UserAuthentication.models.dto.UserReturnedDto;
import com.example.UserAuthentication.publish.NewUserEmailGateway;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;
    private final ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private final NewUserEmailGateway gateway;
    public MyUserDetailsService(UserDetailsRepository userDetailsRepository, ModelMapper modelMapper, NewUserEmailGateway gateway) {
        this.userDetailsRepository = userDetailsRepository;
        this.modelMapper = modelMapper;
        this.gateway = gateway;
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

    public long saveUserAndCreateUserScore(UserDto userDetails) {
        UserDetailsImp map = modelMapper.map(userDetails, UserDetailsImp.class);
        map.setUserRole(UserRole.USER_ROLE);
        map.setPassword(passwordEncoder.encode(map.getPassword()));
        UserDetailsImp save = userDetailsRepository.save(map);
        createUserScore(save.getEmail());
        return save.getId();
    }

    private void createUserScore(String email) {
        gateway.inform(new NewUserMessage(email));

    }
}

