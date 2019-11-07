package me.asite.api.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseSearch {

    private int year;
    private int term;
    private String grade;
    private String major;

    public CourseSearch(int year, int term, String grade, String major) {
        this.year = year;
        this.term = term;
        this.grade = grade;
        this.major = major;
    }
}
