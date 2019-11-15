package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.AttendanceState;
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

    public Long addTimetable(Long studentId, Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(CannotFindByIDException::new);
        Course course = courseRepository.findById(courseId).get();

        Timetable timetable = Timetable.createTimetable(student, course);

        timetableRepository.save(timetable);

        return timetable.getId();
    }

    public void deleteTimetable(Long timetableId) {
        timetableRepository.deleteById(timetableId);
    }

    public Timetable countAttendance(Long timetableId, AttendanceState state) {
        Timetable timetable = timetableRepository.findById(timetableId).orElseThrow(CannotFindByIDException::new);

        if (updateCount(state, timetable)) return timetable;

        throw new AttendanceFailException();

    }

    public Timetable findOne(Long timetableId) {
        return timetableRepository.findById(timetableId).orElseThrow(CannotFindByIDException::new);
    }



    private boolean updateCount(AttendanceState state, Timetable timetable) {
        return countAttendance(state, timetable);
    }

    private boolean countAttendance(AttendanceState state, Timetable timetable) {
        if (state == AttendanceState.ATTENDANCE) {
            timetable.setAttendanceCount(timetable.getAttendanceCount() + 1);
            return true;
        } else if (state == AttendanceState.LATELESS) {
            timetable.setLatelessCount(timetable.getLatelessCount() + 1);
            return true;
        } else if (state == AttendanceState.ABSENT) {
            timetable.setAbsentCount(timetable.getAbsentCount() + 1);
            return true;
        }
        return false;
    }

    public List<Timetable> findAllWithCourseByStudentId(Long studentId) {
        return timetableRepository.findAllWithCourseByStudentId(studentId);
    }
}
