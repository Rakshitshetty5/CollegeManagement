package com.example.rakshit.CollegeManagementSystem.services;

import com.example.rakshit.CollegeManagementSystem.dto.CreateStudentRequestDto;
import com.example.rakshit.CollegeManagementSystem.dto.StudentResponseDto;
import com.example.rakshit.CollegeManagementSystem.dto.UpdateStudentDto;
import com.example.rakshit.CollegeManagementSystem.entities.AdmissionRecordEntity;
import com.example.rakshit.CollegeManagementSystem.entities.StudentEntity;
import com.example.rakshit.CollegeManagementSystem.exceptions.ResourceNotFoundException;
import com.example.rakshit.CollegeManagementSystem.repositories.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private CreateStudentRequestDto studentRequestDto;
    private UpdateStudentDto updateStudentDto;
    private StudentEntity mockStudent;

    @BeforeEach
    void setUp() {

        studentRequestDto = new CreateStudentRequestDto(
        );
        studentRequestDto.setName("Luffy San");
        studentRequestDto.setFees(BigDecimal.valueOf(2000.0));

        updateStudentDto = new UpdateStudentDto(
        );
        updateStudentDto.setName("Zoro San");
        updateStudentDto.setFees(BigDecimal.valueOf(3000.0));

        mockStudent = new StudentEntity();
        mockStudent.setName("Luffy San");
        mockStudent.setId(1L);

        AdmissionRecordEntity admission = new AdmissionRecordEntity();
        admission.setFees(BigDecimal.valueOf(2000));

        admission.setStudent(mockStudent);
        mockStudent.setAdmissionRecord(admission);
    }

    @Test
    void testCreateStudent_whenValidStudent_ThenCreateStudent(){
        //arrange
        //below line says whenever save is called do something
        when(studentRepository.save(any(StudentEntity.class)))
                //below line returns argument passed to save method which would be student
                .thenAnswer(invocation -> invocation.getArgument(0));

        //act
        studentService.createStudent(studentRequestDto);

        //Assert
        //below code for intercepting what as passed inside save method
        ArgumentCaptor<StudentEntity> captor = ArgumentCaptor.forClass(StudentEntity.class);
        verify(studentRepository).save(captor.capture()); //verify save was called and store the object passed to save inside captor
        StudentEntity student = captor.getValue(); //get object from captor

        assertThat(student.getName()).isEqualTo(studentRequestDto.getName());
        assertThat(student.getAdmissionRecord()).isNotNull();
        assertThat(student.getAdmissionRecord().getFees()).isEqualTo(studentRequestDto.getFees());
        assertThat(student.getAdmissionRecord().getStudent()).isSameAs(student);
    }

    @Test
    void testGetStudentById_whenStudentIdIsPresent_returnStudent(){
        //arrange
        Long id = mockStudent.getId();
        when(studentRepository.findById(id)).thenReturn(Optional.of(mockStudent));

        //act
        StudentResponseDto response = studentService.getStudentById(1L);

        //Assert
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(mockStudent.getName());
        assertThat(response.getId()).isEqualTo(id);
        verify(studentRepository).findById(id);
    }

    @Test
    void testGetStudentById_whenStudentIdIsNotPresent_throwException(){
        //arrange
        when(studentRepository.findById(anyLong())).thenReturn(Optional.empty());

        //act and assert
        assertThatThrownBy(() -> studentService.getStudentById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student Not Found");

        verify(studentRepository).findById(1L);
    }

    @Test
    void testGetAllStudents(){
        //arrange


        List<StudentEntity> students = List.of(mockStudent);

        Page<StudentEntity> studentPage = new PageImpl<>(
                students,
                PageRequest.of(0, 10, Sort.by("name").ascending()),
                students.size()
        );

        when(studentRepository.findAll(any(Pageable.class))).thenReturn(studentPage);

        //Act
        Page<StudentResponseDto> result = studentService.getAllStudents(0, 10, "name", "asc");

        //Assert

        //1.verifying response
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getTotalPages()).isEqualTo(1);
        assertThat(result.getContent()).hasSize(1);

        StudentResponseDto response = result.getContent().get(0);
        assertThat(response.getName()).isEqualTo(mockStudent.getName());

        //2.verifying arguments to findAll
        ArgumentCaptor<Pageable> captor = ArgumentCaptor.forClass(Pageable.class);

        verify(studentRepository).findAll(captor.capture());

        Pageable pageable = captor.getValue();

        assertThat(pageable.getPageNumber()).isEqualTo(0);
        assertThat(pageable.getPageSize()).isEqualTo(10);
        Sort.Order order = pageable.getSort().getOrderFor("name");
        assertThat(order).isNotNull();
        assertThat(order.isAscending()).isTrue();
    }

    @Test
    void testUpdateStudent_whenStudentDoesntExists(){
        //arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        //act and assert
        assertThatThrownBy(() -> studentService.updateStudent(1L, updateStudentDto))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student Not Found");

        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testUpdateStudent_whenStudentExists_thenReturnUpdatedStudent() {

        // Arrange
        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(mockStudent));

        // Act
        StudentResponseDto updatedStudent =
                studentService.updateStudent(1L, updateStudentDto);

        // Assert
        assertThat(updatedStudent).isNotNull();
        assertThat(updatedStudent.getName())
                .isEqualTo(updateStudentDto.getName());

        assertThat(mockStudent.getName())
                .isEqualTo(updateStudentDto.getName());

        assertThat(mockStudent.getAdmissionRecord().getFees())
                .isEqualByComparingTo(updateStudentDto.getFees());

        verify(studentRepository).findById(1L);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void testDeleteStudent_whenStudentDoesNotExists_thenThrowException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

//        act
        assertThatThrownBy(() -> studentService.deleteStudent(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Student Not Found");

        verify(studentRepository, never()).delete(any());
    }


    @Test
    void testDeleteEmployee_whenEmployeeIsValid_thenDeleteEmployee() {
//        arrange
        when(studentRepository.findById(1L)).thenReturn(Optional.of(mockStudent));

        studentService.deleteStudent(1L);

        verify(studentRepository).delete(mockStudent);
    }
}