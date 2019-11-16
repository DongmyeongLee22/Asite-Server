package me.asite.timetable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import me.asite.attendance.Attendance;
import me.asite.attendance.AttendanceListSerializer;
import me.asite.course.Course;
import me.asite.student.Student;
import me.asite.student.StudentSerializer;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
public class Timetable {

    @Id
    @GeneratedValue
    @Column(name = "timetable_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    @JsonSerialize(using = StudentSerializer.class)
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "timetable", cascade = ALL)
    @JsonSerialize(using = AttendanceListSerializer.class)
    private List<Attendance> attendanceList = new ArrayList<>();

    private int attendanceCount;

    private int latelessCount;

    private int absentCount;

    private int earlyEnd;

    //--- 연관관계 편의 메서드 ---//
    public void setStudent(Student student){
        this.student = student;
        student.getTimetableList().add(this);
    }

    public void setCourse(Course course){
        this.course = course;
    }

    //--- 생성 메서드 ---//
    public static Timetable createTimetable(Student student, Course course){
            Timetable timetable = new Timetable();
            timetable.setStudent(student);
            timetable.setCourse(course);
            timetable.attendanceCount = 0;
            timetable.latelessCount = 0;
            timetable.absentCount = 0;
        timetable.earlyEnd = 0;

            return timetable;
    }

    public void addAttendance(Attendance attendance) {
        this.attendanceList.add(attendance);
        attendance.setTimetable(this);
    }

}
