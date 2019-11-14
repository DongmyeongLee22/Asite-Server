package me.asite.course;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseAddRequestDto {

    private Long id;
    private int classNumber;
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


    public Course toEntity() {
        return Course.builder()
                .classNumber(classNumber)
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
