package me.asite.service;

import me.asite.attendance.*;
import me.asite.course.Course;
import me.asite.course.CourseService;
import me.asite.exception.CannotFindByIDException;
import me.asite.student.Student;
import me.asite.student.StudentService;
import me.asite.timetable.Timetable;
import me.asite.timetable.TimetableService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TimetableServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TimetableService timetableService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EntityManager em;

    @Test
    public void 시간표_추가() throws Exception {

        //when
        Long timetableId = getTimetableId();

        //then
        Timetable findTimetable = timetableService.findOne(timetableId);
        System.out.println(findTimetable.getCourse().getTitle());
        System.out.println(findTimetable.getStudent().getName());
        assertThat(findTimetable.getAttendanceCount(), equalTo(0));
        assertThat(findTimetable.getAbsentCount(), equalTo(0));
        assertThat(findTimetable.getLatelessCount(), equalTo(0));
    }

    @Test(expected = CannotFindByIDException.class)
    public void 시간표_삭제() throws Exception{

        //when
        Long timetableId = getTimetableId();
        timetableService.deleteTimetable(timetableId);
        timetableService.findOne(timetableId);

        //then
        fail("예외가 발생해야 한다");
        }

        @Test
        public void 출석체크() throws Exception{
            //when
            Long timetableId = getTimetableId();
            AttendanceAddRequestDto attendAddDto = AttendanceAddRequestDto.builder()
                    .attendanceDate("12-12")
                    .startTime("10:00")
                    .endTime("12:00")
                    .attendanceState(AttendanceState.ATTENDANCE)
                    .attendanceEndState(AttendanceEndState.EARLY)
                    .build();
            Timetable timetable = timetableService.countAttendance(timetableId, attendAddDto.getAttendanceState());

            attendanceService.attendanceCheck(timetable, attendAddDto);

            //then
            Timetable findTimetable = timetableService.findOne(timetableId);
            Attendance findAttendance = findTimetable.getAttendanceList().get(0);

            assertThat(findTimetable.getAttendanceCount(), is(1));
            assertThat(findTimetable.getLatelessCount(), is(0));
            assertThat(findAttendance.getAttendanceEndState(), is(AttendanceEndState.EARLY));
        }



    private Long getTimetableId() {
        Student student = createStudent("12491", "123123", "정보통신공학과");
        Course course1 = createCourse("전자장론", "3학점", "정보통신공학과");
        studentService.join(student);
        courseService.courseAdd(course1);
        em.flush(); em.clear();
        return timetableService.addTimetable(student.getId(), course1.getId());
    }

    private Course createCourse(String title, String credit, String major) {
        return Course.builder()
                .title(title)
                .credit(credit)
                .major(major)
                .build();
    }

    private Student createStudent(String studentNumber, String password, String major) {
        return Student.builder()
                .studentNumber(studentNumber)
                .password(password)
                .major(major)
                .build();
    }


}