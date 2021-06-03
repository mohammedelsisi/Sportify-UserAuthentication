package com.example.UserAuthentication.entity;

import com.example.UserAuthentication.models.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.AccessType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.*;

@Entity
@Getter
@Setter
@AccessType(AccessType.Type.FIELD)
@Table(name = "User_Credentials")
public class UserDetailsImp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;
    private String userName;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private UserRole userRole;

}
