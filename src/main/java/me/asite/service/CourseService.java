package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.api.controller.CourseSearch;
import me.asite.domain.Course;
import me.asite.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;

    public Long courseAdd(Course course){
        courseRepository.save(course);
        return course.getId();
    }

    public Course findOne(Long courseId){
        return courseRepository.findOne(courseId);
    }


    public List<Course> findCourses(CourseSearch courseSearch) {
        return courseRepository.findCourses(courseSearch);
    }
}
