package me.asite.attendance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
public class AttendanceCheckRequestDto {


    @NotEmpty
    private String attendanceDate;
    @NotEmpty
    private String startTime;
    @NotEmpty
    private String endTime;

}
