package me.asite.timetable;

import me.asite.attendance.*;
import me.asite.common.TestDescription;
import me.asite.course.Course;
import me.asite.course.repository.CourseRepository;
import me.asite.exception.CannotFindByIDException;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TimetableServiceTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    TimetableService timetableService;

    @Autowired
    AttendanceRepository attendanceRepository;

    @Autowired
    EntityManager entityManager;

    @After
    public void cleanUp() {
        timetableRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
        attendanceRepository.deleteAll();
    }

    @Test
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
    public void updateTimetable() throws Exception {
        //given
        Student student = getStudentAndJoin("201412345", "password");
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Timetable addedTimetable = timetableService.addTimetable(student.getId(), course.getId());
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        AttendanceCheckRequestDto attendancedto = AttendanceCheckRequestDto.builder()
                .attendanceDate(LocalDate.now())
                .startTime(startTime)
                .endTime(endTime)
                .attendanceState(AttendanceState.ATTENDANCE)
                .attendanceEndState(AttendanceEndState.EARLY)
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

    @Test(expected = CannotFindByIDException.class)
    @TestDescription("시간표를 삭제하고 다시 조회하면 예외가 발생해야 한다")
    public void deleteTimetable() throws Exception {
        //given
        Student student = getStudentAndJoin("201412345", "password");
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");

        //when
        Timetable addedTimetable = timetableService.addTimetable(student.getId(), course.getId());
        timetableService.deleteTimetable(addedTimetable.getId());
        timetableService.findOne(addedTimetable.getId());

        //then
        fail("예외가 발생해야 한다.");
    }

    @Test
    @TestDescription("시간표 삭제시 영속성 전이로 인해 그 시간표의 출석 내역도 같이 삭제되어야 한다")
    public void deleteTimetableWithAttendacne() throws Exception {
        //given
        Student student = getStudentAndJoin("201412345", "password");
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Timetable addedTimetable = timetableService.addTimetable(student.getId(), course.getId());
        LocalTime startTime = LocalTime.of(10, 0);
        LocalTime endTime = LocalTime.of(11, 0);
        AttendanceCheckRequestDto attendancedto = AttendanceCheckRequestDto.builder()
                .attendanceDate(LocalDate.now())
                .startTime(startTime)
                .endTime(endTime)
                .attendanceState(AttendanceState.ATTENDANCE)
                .attendanceEndState(AttendanceEndState.EARLY)
                .build();

        //when
        Timetable updatedTimetable = timetableService.attendanceCheck(addedTimetable.getId(), attendancedto);
        Long attendanceId = updatedTimetable.getAttendanceList().get(0).getId();
        timetableService.deleteTimetable(addedTimetable.getId());
        Optional<Attendance> findAttendacne = attendanceRepository.findById(attendanceId);

        //then
        assertThat(findAttendacne).isEmpty();
    }

    @Test
    @TestDescription("학생의 시간표 전체를 조회하는 테스트")
    public void query_Timetable() throws Exception {
        //given
        Student student = getStudentAndJoin("201412345", "password");
        Course course1 = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Course course2 = createCourseAndAdd("4학년", "컴퓨터공학과", "자료구조");
        Course course3 = createCourseAndAdd("4학년", "컴퓨터공학과", "디자인패턴");

        //when
        timetableService.addTimetable(student.getId(), course1.getId());
        timetableService.addTimetable(student.getId(), course2.getId());
        timetableService.addTimetable(student.getId(), course3.getId());
        entityManager.flush();
        entityManager.clear();
        //then
        List<Timetable> timetables = timetableService.findAllWithCourseByStudentId(student.getId());

        assertThat(timetables.size()).isEqualTo(3);
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