package me.asite.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.asite.domain.Course;

@Data
@NoArgsConstructor
public class CourseDto {

    private Long id;
    private int number;
    private int year;
    private int semester;
    private String subject;
    private String major;
    private String grade;
    private String title;
    private String credit;
    private String location;
    private String professor;
    private String time;

    public CourseDto(Course course) {
        id = course.getId();
        number = course.getNumber();
        year = course.getYear();
        semester = course.getSemester();
        subject = course.getSubject();
        major = course.getMajor();
        grade = course.getGrade();
        title = course.getTitle();
        credit = course.getCredit();
        location = course.getLocation();
        professor = course.getProfessor();
        time = course.getTime();
    }


    public Course toEntity() {
        return Course.builder()
                .number(number)
                .year(year)
                .semester(semester)
                .subject(subject)
                .major(major)
                .grade(grade)
                .title(title)
                .credit(credit)
                .location(location)
                .professor(professor)
                .time(time)
                .build();
    }

}
