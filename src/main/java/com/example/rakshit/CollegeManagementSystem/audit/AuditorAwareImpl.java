package com.example.rakshit.CollegeManagementSystem.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor(){
        return Optional.of("Admin"); //need to get this from auth
    }

}
