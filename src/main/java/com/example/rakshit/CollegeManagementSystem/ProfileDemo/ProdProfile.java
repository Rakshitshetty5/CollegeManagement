package com.example.rakshit.CollegeManagementSystem.ProfileDemo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class ProdProfile implements ProfileServiceImp{

    @Override
    public String showProfile(){
        return "Prod";
    }

}
