package me.asite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @JoinColumn(name = "schedule_attendance_id")
    private ScheduleAttendace scheduleAttendace;

    private String attendanceDate;

    private String startTime;

    private String endTime;

    private AttendanceState attendanceState;

    public void setScheduleAttendace(ScheduleAttendace scheduleAttendace){
        this.scheduleAttendace = scheduleAttendace;
        scheduleAttendace.getAttendanceList().add(this);
    }

    public static Attendance createAttendnace(ScheduleAttendace scheduleAttendace, String attendanceDate, String startTime
                                              , String endTime, AttendanceState attendanceState){
        Attendance attendance = new Attendance();
        attendance.attendanceDate = attendanceDate;
        attendance.startTime = startTime;
        attendance.endTime = endTime;
        attendance.attendanceState = attendanceState;

        attendance.setScheduleAttendace(scheduleAttendace);

        return attendance;
    }


}
