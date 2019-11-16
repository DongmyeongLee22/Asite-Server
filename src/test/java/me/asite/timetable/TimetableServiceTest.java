package me.asite.timetable;

import me.asite.attendance.Attendance;
import me.asite.attendance.AttendanceCheckRequestDto;
import me.asite.attendance.AttendanceEndState;
import me.asite.course.Course;
import me.asite.course.repository.CourseRepository;
import me.asite.student.Student;
import me.asite.student.StudentRepository;
import me.asite.student.StudentRole;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TimetableServiceTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    TimetableService timetableService;

    @After
    public void cleanUp() {
        timetableRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    @Transactional
    public void addTimetable() throws Exception {
        //given
        Student student = getStudentAndJoin("201412345", "password");
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");

        //when
        Timetable addedTimetable = timetableService.addTimetable(student.getId(), course.getId());

        //then
        Timetable getTimetable = timetableService.findOne(addedTimetable.getId());
        assertThat(getTimetable.getCourse().getTitle()).isEqualTo(course.getTitle());
        assertThat(getTimetable.getStudent().getStudentNumber()).isEqualTo(student.getStudentNumber());
        assertThat(getTimetable.getAttendanceCount()).isEqualTo(0);
        assertThat(getTimetable.getLatelessCount()).isEqualTo(0);
        assertThat(getTimetable.getAbsentCount()).isEqualTo(0);
        assertThat(getTimetable.getEarlyEnd()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void updateTimetable() throws Exception {
        //given
        Student student = getStudentAndJoin("201412345", "password");
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Timetable addedTimetable = timetableService.addTimetable(student.getId(), course.getId());
        String startTime = "10:00";
        String endTime = "11:00";
        AttendanceCheckRequestDto attendancedto = AttendanceCheckRequestDto.builder()
                .attendanceDate("11-16")
                .startTime(startTime)
                .endTime(endTime)
                .build();

        //when
        Timetable updatedTimetable = timetableService.attendanceCheck(addedTimetable.getId(), attendancedto);
        List<Attendance> getAttendanceList = updatedTimetable.getAttendanceList();

        //then
        assertThat(updatedTimetable.getAttendanceCount()).isEqualTo(1);
        assertThat(updatedTimetable.getAbsentCount()).isEqualTo(0);
        assertThat(updatedTimetable.getEarlyEnd()).isEqualTo(1);
        assertThat(getAttendanceList.size()).isEqualTo(1);
        assertThat(getAttendanceList.get(0).getStartTime()).isEqualTo(startTime);
        assertThat(getAttendanceList.get(0).getEndTime()).isEqualTo(endTime);
        assertThat(getAttendanceList.get(0).getAttendanceEndState()).isEqualByComparingTo(AttendanceEndState.EARLY);


    }


    private Course createCourseAndAdd(String grade, String major, String title) {
        Course course = Course.builder()
                .classNumber(103)
                .year(2019)
                .semester(1)
                .subject("전공필수")
                .major(major)
                .grade(grade)
                .title(title)
                .credit("3학점")
                .location("A13-2126")
                .professor("홍길동")
                .time("월:[6](14:00-14:50) 수:[2][3](10:00-11:50)")
                .build();
        return courseRepository.save(course);
    }

    private Student getStudentAndJoin(String studentNumber, String password) {
        Student student = Student.builder()
                .studentNumber(studentNumber)
                .password(password)
                .email("test@email.com")
                .major("컴퓨터 공학과")
                .name("홍길동")
                .roles(Collections.singleton(StudentRole.USER))
                .build();
        return studentRepository.save(student);
    }

}