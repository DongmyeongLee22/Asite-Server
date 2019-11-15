package me.asite.course;

import lombok.RequiredArgsConstructor;
import me.asite.course.dto.CourseAddRequestDto;
import me.asite.course.dto.CourseSearch;
import me.asite.course.repository.CourseRepository;
import me.asite.exception.CannotFindByIDException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;


    public Course courseAdd(CourseAddRequestDto courseDto) {
        Course course = modelMapper.map(courseDto, Course.class);
        return courseRepository.save(course);
    }

    public Course findOne(Long courseId){
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CannotFindByIDException("강의가 존재하지 않습니다."));
    }

    public List<Course> findCourses(CourseSearch courseSearch) {
        return courseRepository.findCourses(courseSearch);
    }

    public Page<Course> findCourses(CourseSearch courseSearch, Pageable pageable) {
        return courseRepository.findCourses(courseSearch, pageable);
    }
}
