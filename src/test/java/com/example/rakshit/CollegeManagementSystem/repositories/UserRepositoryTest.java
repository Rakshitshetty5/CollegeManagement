package com.example.rakshit.CollegeManagementSystem.repositories;

import com.example.rakshit.CollegeManagementSystem.entities.User;
import com.example.rakshit.CollegeManagementSystem.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Import(TestcontainersConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace =  AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp(){
        user = User.builder()
                .email("abc@gmail.com")
                .password("Test@123")
                .roles(Set.of(Role.ADMIN))
                .build();
    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnUser(){
        //Arrange
        userRepository.save(user);

        //Act
        Optional<User> userResult = userRepository.findByEmail(user.getEmail());

        //Asset
        assertThat(userResult).isNotNull();
        assertThat(userResult.get().getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void testFindByEmail_whenEmailIsNotPresent_thenReturnNull(){
        //Given
        String email = "random@email.com";

        //Act
        Optional<User> userResult = userRepository.findByEmail(email);

        //Assert
        assertThat(userResult).isEmpty();
    }
}