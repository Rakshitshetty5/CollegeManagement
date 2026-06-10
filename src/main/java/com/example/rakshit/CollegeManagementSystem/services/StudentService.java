package com.example.rakshit.CollegeManagementSystem.services;

import com.example.rakshit.CollegeManagementSystem.dto.CreateStudentRequestDto;
import com.example.rakshit.CollegeManagementSystem.dto.StudentResponseDto;
import com.example.rakshit.CollegeManagementSystem.dto.UpdateStudentDto;
import com.example.rakshit.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.rakshit.CollegeManagementSystem.entities.StudentEntity;
import com.example.rakshit.CollegeManagementSystem.repositories.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    private StudentResponseDto toDto(StudentEntity student){
        return StudentResponseDto.builder()
                .id(student.getId())
                .name(student.getName())
                .fees(student.getAdmissionRecord() != null ? student.getAdmissionRecord().getFees() : null)
                .build();
    }

    @Transactional
    public StudentResponseDto createStudent(CreateStudentRequestDto dto){
        StudentEntity student = new StudentEntity();
        student.setName(dto.getName());

        AdmissionRecordEntity admission = new AdmissionRecordEntity();
        admission.setFees(dto.getFees());

        admission.setStudent(student);
        student.setAdmissionRecord(admission);

        StudentEntity savedStudent = studentRepository.save(student);

        return toDto(savedStudent);
    }

    @Transactional
    public StudentResponseDto getStudentById(Long id){
        StudentEntity student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student Not Found"));
        return toDto(student);
    }

    @Transactional
    public Page<StudentResponseDto> getAllStudents(int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return studentRepository.findAll(pageable).map(this::toDto);
    }

    @Transactional
    public StudentResponseDto updateStudent(Long id, UpdateStudentDto dto){
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));

        student.setName(dto.getName());

        AdmissionRecordEntity admissionRecord = student.getAdmissionRecord();
        if(admissionRecord != null) admissionRecord.setFees(dto.getFees());

        return toDto(student);
    }

    @Transactional
    public void deleteStudent(Long id){
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student Not Found"));
        studentRepository.delete(student);
    }
}