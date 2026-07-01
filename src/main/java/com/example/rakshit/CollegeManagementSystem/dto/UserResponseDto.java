package com.example.rakshit.CollegeManagementSystem.dto;

import lombok.*;

import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
public class UserResponseDto {

    private UUID id;
    private String email;

}
