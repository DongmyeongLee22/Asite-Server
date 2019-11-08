package me.asite.repository;

import me.asite.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findAllBystudentNumber(String studentNumber);

    List<Student> findAllBystudentNumberAndPassword(String studentNumber, String password);

}
