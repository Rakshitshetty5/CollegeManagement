package com.example.rakshit.CollegeManagementSystem.controllers;

import com.example.rakshit.CollegeManagementSystem.dto.CreateStudentRequestDto;
import com.example.rakshit.CollegeManagementSystem.dto.StudentResponseDto;
import com.example.rakshit.CollegeManagementSystem.dto.UpdateStudentDto;
import com.example.rakshit.CollegeManagementSystem.services.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/students")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public StudentResponseDto createStudent(
            @RequestBody CreateStudentRequestDto student
    ){
        return studentService.createStudent(student);
    }

    @GetMapping(path = "/{id}")
    public StudentResponseDto getStudentById(
            @PathVariable Long id
    ){
        return studentService.getStudentById(id);
    }

    @GetMapping
    public Page<StudentResponseDto> getAllStudents(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        return studentService.getAllStudents(pageNumber,
                pageSize,
                sortBy,
                sortDir);
    }

    @PutMapping(path = "/{id}")
    public StudentResponseDto updateStudent(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStudentDto dto
            ){
        return studentService.updateStudent(id, dto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
    }
}
