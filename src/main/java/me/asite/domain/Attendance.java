package me.asite.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.asite.domain.state.AttendanceState;
import me.asite.domain.state.FinishState;

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
    private ScheduleAttendance scheduleAttendance;

    private String attendanceDate;

    private String startTime;

    private String endTime;

    private AttendanceState attendanceState;

    private FinishState finishState;

    public void setScheduleAttendance(ScheduleAttendance scheduleAttendance){
        this.scheduleAttendance = scheduleAttendance;
        scheduleAttendance.getAttendanceList().add(this);
    }

    public static Attendance createAttendnace(ScheduleAttendance scheduleAttendance, String attendanceDate, String startTime
                                              , String endTime, AttendanceState attendanceState, FinishState finishState){
        Attendance attendance = new Attendance();
        attendance.attendanceDate = attendanceDate;
        attendance.startTime = startTime;
        attendance.endTime = endTime;
        attendance.attendanceState = attendanceState;
        attendance.finishState = finishState;

        attendance.setScheduleAttendance(scheduleAttendance);

        return attendance;
    }


}
