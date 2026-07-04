package com.example.rakshit.CollegeManagementSystem.ProfileDemo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("Dev")
public class ProfileDev implements ProfileServiceImp{

    @Override
    public String showProfile(){
        return "Dev";
    }
}
