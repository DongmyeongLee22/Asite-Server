package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.domain.Student;
import me.asite.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    @Transactional
    public Long join(Student student) {
        studentRepository.save(student);
        return student.getId();
    }


    public Student findOne(Long studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new LoginFailedException("계정이 존재 하지 않거나 "));
    }
}
