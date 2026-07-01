package com.example.rakshit.CollegeManagementSystem.repositories;

import com.example.rakshit.CollegeManagementSystem.entities.User;
import com.example.rakshit.CollegeManagementSystem.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionRepository extends JpaRepository<Session, UUID> {

    List<Session> findByUser(User user);

    Optional<Session> findByRefreshToken(String refreshToken);
}
