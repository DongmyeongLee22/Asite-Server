package me.asite.attendance;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.asite.timetable.Timetable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {

    @Id
    @GeneratedValue
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    private String attendanceDate;

    private String startTime;

    private String endTime;

    private AttendanceState attendanceState;

    private AttendanceEndState attendanceEndState;

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}
