package com.example.rakshit.CollegeManagementSystem;

import com.example.rakshit.CollegeManagementSystem.dto.UserSignUpDto;
import com.example.rakshit.CollegeManagementSystem.enums.Role;
import com.example.rakshit.CollegeManagementSystem.entities.User;
import com.example.rakshit.CollegeManagementSystem.services.AdminService;
import com.example.rakshit.CollegeManagementSystem.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CollegeManagementSystemApplicationTests {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

    @Test
    void testUserRolesAreSaved() {
        UserSignUpDto dto = new UserSignUpDto();
        dto.setEmail("test@example.com");
        dto.setPassword("password");
        dto.setRoles(Set.of(Role.USER, Role.ADMIN));
        
        adminService.signUp(dto);
        
        User savedUser = userRepository.findByEmail("test@example.com").orElseThrow();
        assertThat(savedUser.getRoles()).containsExactlyInAnyOrder(Role.USER, Role.ADMIN);
    }
}
