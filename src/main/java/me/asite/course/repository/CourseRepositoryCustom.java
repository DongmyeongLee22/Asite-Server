package me.asite.course.repository;

import me.asite.course.Course;
import me.asite.course.dto.CourseSearch;

import java.util.List;


public interface CourseRepositoryCustom {

    List<Course> findCourses(CourseSearch courseSearch);
}
