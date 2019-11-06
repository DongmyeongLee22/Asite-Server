package me.asite.service;

import me.asite.domain.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void 회원가입() throws Exception{
        //given
        Student student = Student.builder()
                .name("홍길동")
                .studentId("123123")
                .build();
        //when
        Long stId = studentService.join(student);

        //then
        Student findStudent = studentService.findOne(stId);
        assertThat(findStudent.getName()).isEqualTo(student.getName());
        assertThat(findStudent.getStudentId()).isEqualTo(student.getStudentId());
    }




}