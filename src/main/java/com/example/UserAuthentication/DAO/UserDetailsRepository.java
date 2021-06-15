package com.example.UserAuthentication.DAO;

import com.example.UserAuthentication.models.entity.UserDetailsImp;
import com.example.UserAuthentication.models.dto.UserReturnedDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface UserDetailsRepository extends JpaRepository<UserDetailsImp,Long> {
    UserDetailsImp findByUserName(String username);
    UserReturnedDto findUserById(long id);
    List<UserReturnedDto> findBy();
}
