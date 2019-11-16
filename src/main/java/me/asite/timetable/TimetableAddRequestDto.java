package me.asite.timetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimetableAddRequestDto {

    @NotNull
    private Long studentId;

    @NotNull
    private Long courseId;

}
