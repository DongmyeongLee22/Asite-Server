package me.asite.student;

import me.asite.student.dto.StudentJoinRequestDto;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentServiceTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    StudentService studentService;

    @After
    public void cleanUp() {
        this.studentRepository.deleteAll();
    }

    @Test
    public void findStudent() throws Exception {
        //given
        String studnerNumber = "201412345";
        String paasword = "paasword";
        StudentJoinRequestDto studentDto = StudentJoinRequestDto.builder()
                .studentNumber(studnerNumber)
                .password(paasword)
                .email("email@gamil.com")
                .major("컴퓨터공학과")
                .roles(Collections.singleton(StudentRole.USER))
                .build();
        studentService.join(studentDto);

        //when
        UserDetails userDetails = this.studentService.loadUserByUsername(studnerNumber);

        //then
        assertThat(this.passwordEncoder.matches(paasword, userDetails.getPassword())).isTrue();
    }

    @Test(expected = UsernameNotFoundException.class)
    public void findStudent_Exceoption() throws Exception {
        //when
        this.studentService.loadUserByUsername("e1231d1");

        //then
        fail("예외가 발생해야 한다.");
    }

}