package me.asite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.asite.domain.state.AttendanceEndState;
import me.asite.domain.state.AttendanceState;

import javax.persistence.*;

@Entity
@Getter
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

    public void setTimetable(Timetable timetable){
        this.timetable = timetable;
        timetable.getAttendanceList().add(this);
    }

    public static Attendance createAttendnace(Timetable timetable, String attendanceDate, String startTime
                                              , String endTime, AttendanceState attendanceState, AttendanceEndState attendanceEndState){
        Attendance attendance = new Attendance();
        attendance.attendanceDate = attendanceDate;
        attendance.startTime = startTime;
        attendance.endTime = endTime;
        attendance.attendanceState = attendanceState;
        attendance.attendanceEndState = attendanceEndState;

        attendance.setTimetable(timetable);

        return attendance;
    }


}
