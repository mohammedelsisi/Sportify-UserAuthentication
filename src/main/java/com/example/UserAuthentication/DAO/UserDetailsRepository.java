package com.example.UserAuthentication.DAO;

import com.example.UserAuthentication.entity.UserDetailsImp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetailsImp,Long> {
    UserDetailsImp findByUserName(String username);

}
