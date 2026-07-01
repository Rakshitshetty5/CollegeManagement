package com.example.rakshit.CollegeManagementSystem.dto;

import com.example.rakshit.CollegeManagementSystem.enums.Permission;
import com.example.rakshit.CollegeManagementSystem.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Data
public class UserSignUpDto {
    private String email;
    private String password;
    private Set<Role> roles;
}
