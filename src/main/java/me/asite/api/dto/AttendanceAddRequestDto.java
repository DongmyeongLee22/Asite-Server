package me.asite.api.dto;

import lombok.Builder;
import lombok.Data;
import me.asite.domain.state.AttendanceState;
import me.asite.domain.state.FinishState;

@Data
public class AttendanceAddRequestDto {


    private String attendanceDate;
    private String startTime;
    private String endTime;
    private AttendanceState attendanceState;
    private FinishState finishState;

    @Builder
    public AttendanceAddRequestDto(String attendanceDate, String startTime, String endTime, AttendanceState attendanceState, FinishState finishState) {
        this.attendanceDate = attendanceDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attendanceState = attendanceState;
        this.finishState = finishState;
    }
}
