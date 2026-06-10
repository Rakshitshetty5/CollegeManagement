package com.example.rakshit.CollegeManagementSystem.repositories;

import com.example.rakshit.CollegeManagementSystem.entities.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
