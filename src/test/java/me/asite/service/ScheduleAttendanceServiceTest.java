package me.asite.service;

import me.asite.domain.AttendanceState;
import me.asite.domain.Course;
import me.asite.domain.ScheduleAttendace;
import me.asite.domain.Student;
import me.asite.exception.CannotFindByIDException;
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
public class ScheduleAttendanceServiceTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private ScheduleAttendanceService scheduleAttendanceService;

    @Autowired
    private EntityManager em;

    @Test
    public void 시간표_추가() throws Exception {

        //when
        Long scheduleId1 = getScheduleAttendanceId();

        //then
        ScheduleAttendace findSchedule = scheduleAttendanceService.findOne(scheduleId1);

        assertThat(findSchedule.getAttendanceCount(), equalTo(0));
        assertThat(findSchedule.getAbsentCount(), equalTo(0));
        assertThat(findSchedule.getLatelessCount(), equalTo(0));
    }

    @Test(expected = CannotFindByIDException.class)
    public void 시간표_삭제() throws Exception{

        //when
        Long scheduleId1 = getScheduleAttendanceId();
        scheduleAttendanceService.deleteScheduleAttendance(scheduleId1);
        scheduleAttendanceService.findOne(scheduleId1);

        //then
        fail("예외가 발생해야 한다");
        }

        @Test
        public void 시간표_출석_업데이트() throws Exception{
            //when
            Long scheduleId1 = getScheduleAttendanceId();
            scheduleAttendanceService.updateScheduleAttendance(scheduleId1, AttendanceState.ABSENT);
            scheduleAttendanceService.updateScheduleAttendance(scheduleId1, AttendanceState.LATELESS);

            //then
            ScheduleAttendace findSchedule = scheduleAttendanceService.findOne(scheduleId1);

            assertThat(findSchedule.getAttendanceCount(), is(0));
            assertThat(findSchedule.getLatelessCount(), is(1));
            assertThat(findSchedule.getAbsentCount(), is(1));
        }



    private Long getScheduleAttendanceId() {
        Student student = createStudent("12491", "123123", "정보통신공학과");
        Course course1 = createCourse("전자장론", "3학점", "정보통신공학과");
        studentService.join(student);
        courseService.courseAdd(course1);

        return scheduleAttendanceService.addScheduleAttendance(student.getId(), course1.getId());
    }

    private Course createCourse(String title, String credit, String major) {
        return Course.builder()
                .title(title)
                .credit(credit)
                .major(major)
                .build();
    }

    private Student createStudent(String studentId, String password, String major) {
        return Student.builder()
                .studentId(studentId)
                .password(password)
                .major(major)
                .build();
    }


}