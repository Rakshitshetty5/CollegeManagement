package com.example.rakshit.CollegeManagementSystem.repositories;

import com.example.rakshit.CollegeManagementSystem.entities.ProfessorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<ProfessorEntity, Long> {
}
