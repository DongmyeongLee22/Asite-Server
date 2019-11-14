package me.asite.timetable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.asite.attendance.Attendance;
import me.asite.course.Course;
import me.asite.student.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Timetable {

    @Id
    @GeneratedValue
    @Column(name = "timetable_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "timetable", cascade = ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    private int attendanceCount;

    private int latelessCount;

    private int absentCount;


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

            return timetable;
    }


}
