package me.asite.course.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class CourseSearch {

    @NotNull
    private int year;
    @NotNull
    private int semester;
    @NotEmpty
    private String grade;
    @NotEmpty
    private String major;

    public CourseSearch(int year, int semester, String grade, String major) {
        this.year = year;
        this.semester = semester;
        this.grade = grade;
        this.major = major;
    }
}
