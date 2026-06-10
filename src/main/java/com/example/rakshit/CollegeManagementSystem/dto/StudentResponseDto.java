package com.example.rakshit.CollegeManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class StudentResponseDto {
    private Long id;
    private String name;
    private BigDecimal fees;

}
