package com.example.rakshit.CollegeManagementSystem.dto;

import com.example.rakshit.CollegeManagementSystem.enums.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
@Getter
@Setter
public class UserAuthDto {
    private String email;
    private String password;
    private Set<Role> role;
}
