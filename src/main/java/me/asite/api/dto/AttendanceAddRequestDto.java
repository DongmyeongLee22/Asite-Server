package me.asite.api.dto;

import lombok.Builder;
import lombok.Data;
import me.asite.domain.state.AttendanceEndState;
import me.asite.domain.state.AttendanceState;

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
