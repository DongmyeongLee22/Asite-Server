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
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private EntityManager em;

    @Test
    public void 회원가입() throws Exception{
        //given
        Student student = Student.builder()
                .name("홍길동")
                .studentId("123123")
                .build();
        //when
        studentService.join(student);
        //then
        Student findStudent = studentService.findOne(student.getId());
        assertThat(findStudent.getName()).isEqualTo(student.getName());
        assertThat(findStudent.getStudentId()).isEqualTo(student.getStudentId());
    }

    @Test(expected = IllegalStateException.class)
    public void 회원가입_중복확인() throws Exception{
        //given
        Student student = Student.builder()
                .name("홍길동")
                .studentId("123123")
                .build();
        Student student2 = Student.builder()
                .name("엄복동")
                .studentId("123123")
                .build();
        //when
        studentService.join(student);
        studentService.join(student2);
        //then

        fail("이미 존재하는 회원이라는 예외메시지가 떠야한다");
        }





}