package me.asite.course;

import lombok.Data;

@Data
public class CourseSearch {

    private int year;
    private int semester;
    private String grade;
    private String major;

    public CourseSearch(int year, int semester, String grade, String major) {
        this.year = year;
        this.semester = semester;
        this.grade = grade;
        this.major = major;
    }
}
