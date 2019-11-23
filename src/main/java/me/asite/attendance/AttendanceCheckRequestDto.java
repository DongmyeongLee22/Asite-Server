package me.asite.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
public class AttendanceCheckRequestDto {


    @NotNull
    private Long studentId;
    @NotNull
    private LocalDate attendanceDate;
    @NotNull
    private LocalTime startTime;
    @NotNull
    private LocalTime endTime;
    @NotNull
    private AttendanceState attendanceState;
    @NotNull
    private AttendanceEndState attendanceEndState;

}
