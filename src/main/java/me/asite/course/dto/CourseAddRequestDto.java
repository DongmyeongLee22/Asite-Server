package me.asite.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseAddRequestDto {

    @NotNull
    private int classNumber;
    @NotNull
    private int year;
    @NotNull
    private int semester;
    @NotEmpty
    private String subject;
    @NotEmpty
    private String major;
    @NotEmpty
    private String grade;
    @NotEmpty
    private String title;
    @NotEmpty
    private String credit;
    @NotEmpty
    private String location;
    @NotEmpty
    private String professor;
    @NotEmpty
    private String time;

}
