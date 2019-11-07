package me.asite.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.asite.domain.Course;

@Data
@NoArgsConstructor
public class CourseDto {

    private int devide;
    private int year;
    private int term;
    private String area;
    private String major;
    private String grade;
    private String title;
    private String credit;
    private String room;
    private String professor;
    private String time;

    public CourseDto(Course course) {
        devide = course.getDevide();
        year = course.getYear();
        term = course.getTerm();
        area = course.getArea();
        major = course.getMajor();
        grade = course.getGrade();
        title = course.getTitle();
        credit = course.getCredit();
        room = course.getRoom();
        professor = course.getProfessor();
        time = course.getTime();
    }


    public Course toEntity() {
        return Course.builder()
                .devide(devide)
                .year(year)
                .term(term)
                .area(area)
                .major(major)
                .grade(grade)
                .title(title)
                .credit(credit)
                .room(room)
                .professor(professor)
                .time(time)
                .build();
    }

}
