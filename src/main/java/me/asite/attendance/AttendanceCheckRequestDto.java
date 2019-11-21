package me.asite.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
public class AttendanceCheckRequestDto {


    @NotNull
    private Long studentId;
    @NotEmpty
    private String attendanceDate;
    @NotEmpty
    private String startTime;
    @NotEmpty
    private String endTime;

}
