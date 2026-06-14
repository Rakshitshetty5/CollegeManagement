package com.example.rakshit.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
public class ProfessorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "professor", cascade = CascadeType.ALL)
    private List<SubjectEntity> subjects = new ArrayList<>();

    @ManyToMany(mappedBy = "professors")
    private List<StudentEntity> students = new ArrayList<>();
}
