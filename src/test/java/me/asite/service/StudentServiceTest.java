package me.asite.service;

import me.asite.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Student student = Student.builder()
                .name("홍길동")
                .studentNumber("123123")
                .build();
        //when
        studentService.join(student);
        //then
        Student findStudent = studentService.findOne(student.getId());
        assertThat(findStudent.getName()).isEqualTo(student.getName());
        assertThat(findStudent.getStudentNumber()).isEqualTo(student.getStudentNumber());
    }

    @Test
    public void 회원가입_중복확인() throws Exception {
        //given
        Student student = Student.builder()
                .name("홍길동")
                .studentNumber("123123")
                .build();
        Student student2 = Student.builder()
                .name("엄복동")
                .studentNumber("123123")
                .build();
        //when
        studentService.join(student);
        boolean result = studentService.vailidateJoin(student2.getStudentNumber());

        //then
        assertThat(result).isEqualTo(false);

    }


}