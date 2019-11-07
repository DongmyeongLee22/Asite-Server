package me.asite.api.controller;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.CourseDto;
import me.asite.domain.Course;
import me.asite.repository.CourseRepository;
import me.asite.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class CourseApiController {

    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @PostMapping("/course/add")
    public void addRequest(@RequestBody CourseDto dto){
        courseService.courseAdd(dto.toEntity());
    }

    @GetMapping("/course/list")
    public List<CourseDto> getCourseList(@ModelAttribute CourseSearch courseSearch){
        List<Course> courses = courseService.findCourses(courseSearch);
        return courses.stream()
                .map(CourseDto::new)
                .collect(toList());
    }

}
