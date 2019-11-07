package me.asite.api.controller;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.CourseDto;
import me.asite.domain.Course;
import me.asite.repository.CourseRepository;
import me.asite.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<Course> getCourseList(@ModelAttribute CourseSearch courseSearch){
        List<Course> all = courseRepository.findAll();
        return all;
    }

}
