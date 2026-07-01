package com.example.rakshit.CollegeManagementSystem.services;

import com.example.rakshit.CollegeManagementSystem.entities.User;
import com.example.rakshit.CollegeManagementSystem.entities.Session;
import com.example.rakshit.CollegeManagementSystem.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT = 2;

    //on each login
    public void generateNewSession(User user, String refreshToken){
        List<Session> userSessions = sessionRepository.findByUser(user);

        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));

            Session leastRecentlyUsedSession = userSessions.getFirst();

            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .user(user)
                .lastUsedAt(LocalDateTime.now())
                .refreshToken(refreshToken)
                .build();

        sessionRepository.save(newSession);
    }

    //on each accessToken refresh
    public void validateSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found"));

        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
