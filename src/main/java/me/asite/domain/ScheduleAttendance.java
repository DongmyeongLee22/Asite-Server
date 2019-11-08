package me.asite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleAttendance {

    @Id
    @GeneratedValue
    @Column(name = "schedule_attendance_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "scheduleAttendance", cascade = ALL)
    private List<Attendance> attendanceList = new ArrayList<>();

    private Integer attendanceCount;

    private Integer latelessCount;

    private Integer absentCount;


    //--- 연관관계 편의 메서드 ---//
    public void setStudent(Student student){
        this.student = student;
        student.getScheduleAttendanceList().add(this);
    }

    public void setCourse(Course course){
        this.course = course;
    }


    //--- 생성 메서드 ---//
    public static ScheduleAttendance createSchedule(Student student, Course course){
            ScheduleAttendance scheduleAttendance = new ScheduleAttendance();
            scheduleAttendance.setStudent(student);
            scheduleAttendance.setCourse(course);
            scheduleAttendance.attendanceCount = 0;
            scheduleAttendance.latelessCount = 0;
            scheduleAttendance.absentCount = 0;

            return scheduleAttendance;
    }


}
