package com.example.rakshit.CollegeManagementSystem.services;

import com.example.rakshit.CollegeManagementSystem.dto.UserResponseDto;
import com.example.rakshit.CollegeManagementSystem.dto.UserSignUpDto;
import com.example.rakshit.CollegeManagementSystem.entities.User;
import com.example.rakshit.CollegeManagementSystem.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AdminService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("User not found"));
    }

    public UserResponseDto signUp(UserSignUpDto userSignUpDto){
        Optional<User> admin = userRepository.findByEmail(userSignUpDto.getEmail());
        if(admin.isPresent()){
            throw new BadCredentialsException("Admin exists");
        }

        User toBeCreatedUser = new User().builder()
                .email(userSignUpDto.getEmail())
                .password(passwordEncoder.encode(userSignUpDto.getPassword()))
                .roles(userSignUpDto.getRoles())
                .build();

        User savedUser = userRepository.save(toBeCreatedUser);

        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getEmail()
        );

    }

    public User getUserById(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException("User with id "+ userId +
                " not found")); //Note: error type is incorrect
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    public User save(User newUser) {
        return userRepository.save(newUser);
    }
}
