package com.example.rakshit.CollegeManagementSystem;

import com.example.rakshit.CollegeManagementSystem.dto.UserSignUpDto;
import com.example.rakshit.CollegeManagementSystem.enums.Role;
import com.example.rakshit.CollegeManagementSystem.entities.User;
import com.example.rakshit.CollegeManagementSystem.services.AdminService;
import com.example.rakshit.CollegeManagementSystem.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class CollegeManagementSystemApplicationTests {

    @BeforeEach
    void setUp(){
        log.info("Starting method, setting up");
    }

    @AfterEach
    void tearDown(){
        log.info("clean up...");
    }

    @BeforeAll
    static void setUpOnce(){
        log.info("Main setup");
    }

    @AfterAll
    static void tearDownOnce(){
        log.info("Tearing down all...");
    }

    @Test
    void testNumberOne(){
        int a = 1;
        int b = 4;

        int result = addTwoNumbers(a, b);

        assertThat(result).isEqualTo(5);
    }

    int addTwoNumbers(int a , int b){
        return a + b;
    }
}
