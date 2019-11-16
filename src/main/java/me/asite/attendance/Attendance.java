package me.asite.attendance;

import lombok.*;
import me.asite.timetable.Timetable;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
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
