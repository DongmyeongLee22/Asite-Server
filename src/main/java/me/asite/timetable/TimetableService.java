package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.*;
import me.asite.course.Course;
import me.asite.course.repository.CourseRepository;
import me.asite.exception.AttendanceFailException;
import me.asite.exception.CannotFindByIDException;
import me.asite.student.Student;
import me.asite.student.StudentRepository;
import org.modelmapper.ModelMapper;
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
    private final ModelMapper modelMapper;

    public Timetable addTimetable(Long studentId, Long courseId) {

        Student student = studentRepository.findById(studentId).orElseThrow(CannotFindByIDException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(CannotFindByIDException::new);

        Timetable timetable = Timetable.createTimetable(student, course);

        return timetableRepository.save(timetable);
    }

    public void deleteTimetable(Long timetableId) {
        timetableRepository.deleteById(timetableId);
    }

    public Timetable updateTimetableAndAttendanceCheck(Long timetableId, AttendanceCheckRequestDto dto) {

        Timetable timetable = timetableRepository.findById(timetableId).orElseThrow(CannotFindByIDException::new);

        if (attendanceCheck(timetable, dto.getAttendanceState(), dto.getAttendanceEndState())) {
            Attendance attendance = modelMapper.map(dto, Attendance.class);
            timetable.addAttendance(attendance);
            attendanceRepository.save(attendance);
            return timetable;
        }

        throw new AttendanceFailException();

    }

    public Timetable findOne(Long timetableId) {
        return timetableRepository.findById(timetableId).orElseThrow(CannotFindByIDException::new);
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

    public List<Timetable> findAllWithCourseByStudentId(Long studentId) {
        return timetableRepository.findAllWithCourseByStudentId(studentId);
    }
}
