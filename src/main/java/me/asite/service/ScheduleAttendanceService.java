package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.domain.Course;
import me.asite.domain.ScheduleAttendance;
import me.asite.domain.Student;
import me.asite.domain.state.AttendanceState;
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

        ScheduleAttendance scheduleAttendance = ScheduleAttendance.createSchedule(student, course);

        scheduleAttendanceRepository.save(scheduleAttendance);

        return scheduleAttendance.getId();
    }

    public void deleteScheduleAttendance(Long scheduleId) {
        scheduleAttendanceRepository.deleteById(scheduleId);
    }

    public ScheduleAttendance countAttendance(Long scheduleId, AttendanceState state) {
        ScheduleAttendance scheduleAttendance = scheduleAttendanceRepository.findById(scheduleId).orElseThrow(CannotFindByIDException::new);

        if (updateCount(state, scheduleAttendance)) return scheduleAttendance;

        throw new AttendanceFailException();

    }

    public ScheduleAttendance findOne(Long scheduleId) {
        return scheduleAttendanceRepository.findById(scheduleId).orElseThrow(CannotFindByIDException::new);
    }

    private boolean updateCount(AttendanceState state, ScheduleAttendance scheduleAttendance) {
        if (state == AttendanceState.ATTENDANCE) {
            scheduleAttendance.setAttendanceCount(scheduleAttendance.getAttendanceCount() + 1);
            return true;
        } else if (state == AttendanceState.LATELESS) {
            scheduleAttendance.setLatelessCount(scheduleAttendance.getLatelessCount() + 1);
            return true;
        } else if (state == AttendanceState.ABSENT) {
            scheduleAttendance.setAbsentCount(scheduleAttendance.getAbsentCount() + 1);
            return true;
        }
        return false;
    }
}
