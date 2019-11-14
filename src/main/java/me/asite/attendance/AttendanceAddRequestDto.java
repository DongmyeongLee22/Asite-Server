package me.asite.attendance;

import lombok.Builder;
import lombok.Data;

@Data
public class AttendanceAddRequestDto {


    private String attendanceDate;
    private String startTime;
    private String endTime;
    private AttendanceState attendanceState;
    private AttendanceEndState attendanceEndState;

    @Builder
    public AttendanceAddRequestDto(String attendanceDate, String startTime, String endTime, AttendanceState attendanceState, AttendanceEndState attendanceEndState) {
        this.attendanceDate = attendanceDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceState = attendanceState;
        this.attendanceEndState = attendanceEndState;
    }
}
