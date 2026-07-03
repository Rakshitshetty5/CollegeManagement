package com.example.rakshit.CollegeManagementSystem.controllers;
import com.example.rakshit.CollegeManagementSystem.dto.CreateStudentRequestDto;
import com.example.rakshit.CollegeManagementSystem.dto.UpdateStudentDto;
import com.example.rakshit.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.rakshit.CollegeManagementSystem.entities.StudentEntity;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webtestclient.autoconfigure.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.math.BigDecimal;

@AutoConfigureWebTestClient(timeout = "100000")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ TestcontainersConfiguration.class, TestSecurityConfig.class})
@ActiveProfiles("test")
public class AbstractIntegrationTesting {

    @Autowired
    WebTestClient webTestClient;
    StudentEntity mockStudent;
    CreateStudentRequestDto studentRequestDto;
    UpdateStudentDto updateStudentDto;
    AdmissionRecordEntity admission;

    @BeforeEach
    void setUp() {
        mockStudent = new StudentEntity();
        mockStudent.setName("Luffy San");

        admission = new AdmissionRecordEntity();
        admission.setFees(BigDecimal.valueOf(2000));

        admission.setStudent(mockStudent);
        mockStudent.setAdmissionRecord(admission);

        studentRequestDto = new CreateStudentRequestDto(
        );
        studentRequestDto.setName("Luffy San");
        studentRequestDto.setFees(BigDecimal.valueOf(2000.0));

        updateStudentDto = new UpdateStudentDto(
        );
        updateStudentDto.setName("Zoro San");
        updateStudentDto.setFees(BigDecimal.valueOf(3000.0));
    }


}
