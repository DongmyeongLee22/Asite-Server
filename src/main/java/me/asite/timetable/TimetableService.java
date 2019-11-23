package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.*;
import me.asite.course.Course;
import me.asite.course.repository.CourseRepository;
import me.asite.exception.AttendanceFailException;
import me.asite.exception.CannotFindByIDException;
import me.asite.student.Student;
import me.asite.student.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TimetableService {

    private final TimetableRepository timetableRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRepository attendanceRepository;

    public Timetable addTimetable(Long studentId, Long courseId) {

        Student student = studentRepository.findById(studentId).orElseThrow(CannotFindByIDException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(CannotFindByIDException::new);

        Timetable timetable = Timetable.createTimetable(student, course);

        return timetableRepository.save(timetable);
    }

    public Timetable attendanceCheck(Long timetableId, AttendanceCheckRequestDto dto) {

        Timetable timetable = timetableRepository.findByIdWithStudentAndCourse(timetableId).orElseThrow(CannotFindByIDException::new);

        AttendanceState attendanceState = dto.getAttendanceState();
        AttendanceEndState attendanceEndState = dto.getAttendanceEndState();

        if (attendanceCheck(timetable, attendanceState, attendanceEndState)) {
            Attendance attendance = makeAttendance(dto, attendanceState, attendanceEndState);
            timetable.addAttendance(attendance);
            attendanceRepository.save(attendance);
            return timetable;
        }

        throw new AttendanceFailException();

    }

    public void deleteTimetable(Long timetabeId) {
        if (!timetableRepository.existsById(timetabeId)) {
            throw new CannotFindByIDException("시간표가 존재하지 않습니다.");
        }
        timetableRepository.deleteById(timetabeId);
    }

    public List<Timetable> findAllWithCourseByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new CannotFindByIDException("학생이 존재하지 않습니다.");
        }
        return timetableRepository.findAllWithCourseByStudentId(studentId);
    }

    public Timetable findOne(Long timetableId) {
        return timetableRepository.findById(timetableId).orElseThrow(CannotFindByIDException::new);
    }

    private Attendance makeAttendance(AttendanceCheckRequestDto dto, AttendanceState attendanceState, AttendanceEndState attendanceEndState) {
        return Attendance.builder()
                .attendanceDate(dto.getAttendanceDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .attendanceState(attendanceState)
                .attendanceEndState(attendanceEndState).build();
    }


    private boolean attendanceCheck(Timetable timetable, AttendanceState attendanceState, AttendanceEndState endState) {

        if (endState == AttendanceEndState.EARLY) {
            timetable.setEarlyEnd(timetable.getEarlyEnd() + 1);
        }

        if (attendanceState == AttendanceState.ATTENDANCE) {
            timetable.setAttendanceCount(timetable.getAttendanceCount() + 1);
            return true;
        } else if (attendanceState == AttendanceState.LATELESS) {
            timetable.setLatelessCount(timetable.getLatelessCount() + 1);
            return true;
        } else if (attendanceState == AttendanceState.ABSENT) {
            timetable.setAbsentCount(timetable.getAbsentCount() + 1);
            return true;
        }
        return false;
    }
}
