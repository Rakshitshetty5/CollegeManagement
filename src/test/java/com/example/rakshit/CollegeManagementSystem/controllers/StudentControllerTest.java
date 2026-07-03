package com.example.rakshit.CollegeManagementSystem.controllers;

import com.example.rakshit.CollegeManagementSystem.entities.StudentEntity;
import com.example.rakshit.CollegeManagementSystem.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest extends AbstractIntegrationTesting{

    @Autowired
    private StudentRepository studentRepository;

    @BeforeEach
    void cleanDB() {
        studentRepository.deleteAll();
    }

    @Test
    void testGetStudentById_success(){
        StudentEntity savedStudent = studentRepository.save(mockStudent);

        webTestClient.get()
                .uri("/students/{id}", savedStudent.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(savedStudent.getId())
                .jsonPath("$.name").isEqualTo(savedStudent.getName());
    }

    @Test
    void testGetStudentById_failure(){
        webTestClient.get()
                .uri("/students/1")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testCreateNewStudent_whenValidStudent_thenCreateStudent(){
        webTestClient.post()
                .uri("/students")
                .bodyValue(studentRequestDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(studentRequestDto.getName());
    }

    @Test
    void testUpdateStudent_whenStudentIdIsValid_thenUpdateStudent(){
        StudentEntity student = studentRepository.save(mockStudent);

        webTestClient.put()
                .uri("/students/{id}", student.getId())
                .bodyValue(updateStudentDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name").isEqualTo(updateStudentDto.getName());
    }

    @Test
    void testUpdateStudent_whenStudentDoesNotExists_thenThrowException(){
        webTestClient.put()
                .uri("/students/999")
                .bodyValue(updateStudentDto)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testDeleteStudent_whenStudentExists_thenDeleteStudent(){
        StudentEntity student = studentRepository.save(mockStudent);
        webTestClient.delete()
                .uri("/students/{id}", student.getId())
                .exchange()
                .expectStatus().isNoContent()
                .expectBody(Void.class);
    }

    @Test
    void testDeleteStudent_whenStudentDoesNotExists_thenThrowException(){
        webTestClient.delete()
                .uri("/students/8888")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testListAllStudents(){
        StudentEntity student = studentRepository.save(mockStudent);
        webTestClient.get()
                .uri("/students")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.content.length()").isEqualTo(1)
                .jsonPath("$.content[0].name").isEqualTo(mockStudent.getName())
                .jsonPath("$.totalElements").isEqualTo(1)
                .jsonPath("$.totalPages").isEqualTo(1)
                .jsonPath("$.size").isEqualTo(10)
                .jsonPath("$.number").isEqualTo(0);
    }




}