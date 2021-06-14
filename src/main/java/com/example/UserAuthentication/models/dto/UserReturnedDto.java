package com.example.UserAuthentication.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;


@Value
public class UserReturnedDto {
    String userName;
    String email;

}
