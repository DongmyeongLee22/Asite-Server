package me.asite.student;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllBystudentNumber(String studentNumber);

    Optional<Student> findByStudentNumber(String studentNumber);

    List<Student> findAllBystudentNumberAndPassword(String studentNumber, String password);

}
