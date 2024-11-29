package com.user.user_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDto {
    private String fullName;
    private String username;

    private String password;

    private String email;

    private List<String> roles;


}