package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.domain.Student;
import me.asite.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public Student join(Student student) {
        validateDuplicateStudent(student);
        return studentRepository.save(student);
    }

    private void validateDuplicateStudent(Student student) {
        List<Student> students = studentRepository.findAllByStudentId(student.getStudentId());
        if (!students.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원 입니다.");
        }
    }


    public Student findOne(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new LoginFailedException("로그인 실패"));
    }

}
