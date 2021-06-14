package com.example.UserAuthentication.models.dto;

import lombok.Data;
import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDto {
    String userName;

    @Email
    String email;

    @NotNull
            @Size
    String password;


}
