package me.asite;

import lombok.RequiredArgsConstructor;
import me.asite.course.Course;
import me.asite.course.CourseService;
import me.asite.course.dto.CourseAddRequestDto;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppRunner implements ApplicationRunner {
    private final CourseService courseService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        createCourseAndAdd(2019, 1, "2학년", "전자공학과", "전자공학");

    }

    private Course createCourseAndAdd(int year, int semester, String grade, String major, String title) {
        CourseAddRequestDto courseDto = CourseAddRequestDto.builder()
                .classNumber(103)
                .year(year)
                .semester(semester)
                .subject("전공필수")
                .major(major)
                .grade(grade)
                .title(title)
                .credit("3학점")
                .location("A13-2126")
                .professor("홍길동")
                .time("월:[6](14:00-14:50) 수:[2][3](10:00-11:50)")
                .build();
        return courseService.courseAdd(courseDto);
    }

}
