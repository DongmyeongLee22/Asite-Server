package me.asite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ScheduleAttendace {

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

    @OneToMany(mappedBy = "scheduleAttendace")
    private List<Attendance> attendanceList;

    private Integer attendanceCount;

    private Integer latelessCount;

    private Integer absentCount;


    //--- 연관관계 편의 메서드 ---//
    public void setStudent(Student student){
        this.student = student;
        student.getScheduleAttendaceList().add(this);
    }

    public void setCourse(Course course){
        this.course = course;
    }


    //--- 생성 메서드 ---//
    public static ScheduleAttendace createSchedule(Student student, Course course){
            ScheduleAttendace scheduleAttendace = new ScheduleAttendace();
            scheduleAttendace.setStudent(student);
            scheduleAttendace.setCourse(course);
            scheduleAttendace.attendanceCount = 0;
            scheduleAttendace.latelessCount = 0;
            scheduleAttendace.absentCount = 0;

            return scheduleAttendace;
    }


}
