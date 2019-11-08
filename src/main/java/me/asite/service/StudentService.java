package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.StudentLoginRequestDto;
import me.asite.api.response.IsSuccessReponse;
import me.asite.api.response.LoginResponseDto;
import me.asite.domain.Student;
import me.asite.exception.LoginFailedException;
import me.asite.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public IsSuccessReponse join(Student student) {
        try {

            studentRepository.save(student);
            return new IsSuccessReponse(true);

        } catch (IllegalStateException e) {

            return new IsSuccessReponse(false);

        }
    }

    public boolean vailidateJoin(String studentNumber) {
        List<Student> students = studentRepository.findAllBystudentNumber(studentNumber);
        return students.isEmpty();
    }

    public Student findOne(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new LoginFailedException("로그인 실패"));
    }

    public LoginResponseDto login(StudentLoginRequestDto dto) {
        List<Student> students = studentRepository.findAllBystudentNumberAndPassword(dto.getStudentNumber(), dto.getPassword());
        if (!students.isEmpty()) {
            return new LoginResponseDto(true, students.get(0).getId(), students.get(0).getName());
        }
        return new LoginResponseDto(false, null, null);
    }
}
