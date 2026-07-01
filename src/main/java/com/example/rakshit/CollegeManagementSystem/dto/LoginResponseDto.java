package com.example.rakshit.CollegeManagementSystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginResponseDto {

    private UUID id;
    private String accessToken;
    private String refreshToken;
}
