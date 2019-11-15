package me.asite.course.repository;

import me.asite.course.Course;
import me.asite.course.dto.CourseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CourseRepositoryCustom {

    List<Course> findCourses(CourseSearch courseSearch);

    Page<Course> findCourses(CourseSearch courseSearch, Pageable pageable);

}
