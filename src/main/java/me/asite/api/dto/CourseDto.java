package me.asite.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.asite.domain.Course;

@Getter
@Setter
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
