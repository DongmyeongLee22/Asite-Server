package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.domain.AttendanceState;
import me.asite.domain.Course;
import me.asite.domain.ScheduleAttendace;
import me.asite.domain.Student;
import me.asite.exception.AttendanceFailException;
import me.asite.exception.CannotFindByIDException;
import me.asite.repository.CourseRepository;
import me.asite.repository.ScheduleAttendanceRepository;
import me.asite.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleAttendanceService {

    private final ScheduleAttendanceRepository scheduleAttendanceRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Long addScheduleAttendance(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(CannotFindByIDException::new);
        Course course = courseRepository.findOne(courseId);

        ScheduleAttendace scheduleAttendace = ScheduleAttendace.createSchedule(student, course);

        scheduleAttendanceRepository.save(scheduleAttendace);

        return scheduleAttendace.getId();
    }

    public void deleteScheduleAttendance(Long scheduleId) {
        scheduleAttendanceRepository.deleteById(scheduleId);
    }

    public void updateScheduleAttendance(Long scheduleId, AttendanceState state) {
        ScheduleAttendace scheduleAttendace = scheduleAttendanceRepository.findById(scheduleId).orElseThrow(CannotFindByIDException::new);

        if (countAttendance(state, scheduleAttendace)) return;

        throw new AttendanceFailException();

    }

    public ScheduleAttendace findOne(Long scheduleId) {
        return scheduleAttendanceRepository.findById(scheduleId).orElseThrow(CannotFindByIDException::new);
    }

    private boolean countAttendance(AttendanceState state, ScheduleAttendace scheduleAttendace) {
        if (state == AttendanceState.ATTENDANCE) {
            scheduleAttendace.setAttendanceCount(scheduleAttendace.getAttendanceCount() + 1);
            return true;
        } else if (state == AttendanceState.LATELESS) {
            scheduleAttendace.setLatelessCount(scheduleAttendace.getLatelessCount() + 1);
            return true;
        } else if (state == AttendanceState.ABSENT) {
            scheduleAttendace.setAbsentCount(scheduleAttendace.getAbsentCount() + 1);
            return true;
        }
        return false;
    }
}
