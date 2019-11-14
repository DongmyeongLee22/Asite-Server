package me.asite.course;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CourseApiController {

    private final CourseService courseService;

    @PostMapping("/course/add")
    public void addRequest(@RequestBody CourseAddRequestDto dto){
        courseService.courseAdd(dto.toEntity());
    }

    @GetMapping("/course/list")
    public List<CourseListRequestDto> getCourseList(@ModelAttribute CourseSearch courseSearch){
        List<Course> courses = courseService.findCourses(courseSearch);
        return courses.stream()
                .map(CourseListRequestDto::new)
                .collect(toList());
    }

}
