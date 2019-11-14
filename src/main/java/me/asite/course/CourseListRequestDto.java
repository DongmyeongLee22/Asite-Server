package me.asite.course;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseListRequestDto {

    private Long id;
    private int classNumber;
    private String major;
    private String grade;
    private String title;
    private String credit;
    private String location;
    private String professor;
    private String time;

    public CourseListRequestDto(Course course) {
        id = course.getId();
        classNumber = course.getClassNumber();
        major = course.getMajor();
        grade = course.getGrade();
        title = course.getTitle();
        credit = course.getCredit();
        location = course.getLocation();
        professor = course.getProfessor();
        time = course.getTime();
    }


}
