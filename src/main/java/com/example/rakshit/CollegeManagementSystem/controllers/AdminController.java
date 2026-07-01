package com.example.rakshit.CollegeManagementSystem.controllers;

import com.example.rakshit.CollegeManagementSystem.dto.UserAuthDto;
import com.example.rakshit.CollegeManagementSystem.dto.UserResponseDto;
import com.example.rakshit.CollegeManagementSystem.dto.LoginResponseDto;
import com.example.rakshit.CollegeManagementSystem.dto.UserSignUpDto;
import com.example.rakshit.CollegeManagementSystem.services.AdminService;
import com.example.rakshit.CollegeManagementSystem.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AuthService authService;

    @Value("${deploy.env}")
    private String deployEnv;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserSignUpDto signUpDto){
        UserResponseDto adminDto = adminService.signUp(signUpDto);
        return ResponseEntity.ok(adminDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login (@RequestBody UserAuthDto authDto, HttpServletRequest request, HttpServletResponse response){
        LoginResponseDto loginResponseDto = authService.login(authDto);

        Cookie cookie = new Cookie("refreshToken", loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure("production".equals(deployEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(@CookieValue(value = "refreshToken", required = false) String refreshToken){
            if(refreshToken == null){
                throw new AuthenticationServiceException("Refresh token not found");
            }

            return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }

}
