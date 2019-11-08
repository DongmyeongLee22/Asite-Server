package me.asite.api.dto;

import lombok.Data;
import me.asite.domain.AttendanceState;
import me.asite.domain.FinishState;

@Data
public class AttendanceAddRequestDto {


    private String attendanceDate;
    private String startTime;
    private String endTime;
    private AttendanceState attendanceState;
    private FinishState finishState;
    private Long scehduleAttendanceId;



}
