package me.asite.course.repository;

import me.asite.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

}
