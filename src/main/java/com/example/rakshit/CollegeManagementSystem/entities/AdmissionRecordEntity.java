package com.example.rakshit.CollegeManagementSystem.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdmissionRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal fees;

    @OneToOne
    @JoinColumn(name = "student_id") //this creates FK;
    @JsonIgnore
    private StudentEntity student;
}
