package com.example.rakshit.CollegeManagementSystem.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateStudentRequestDto {
    private String name;
    private BigDecimal fees;
}
