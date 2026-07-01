package com.example.rakshit.CollegeManagementSystem.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String refreshToken;

    @Column(nullable = false)
    private LocalDateTime lastUsedAt;

    @ManyToOne
    private User user;

}
