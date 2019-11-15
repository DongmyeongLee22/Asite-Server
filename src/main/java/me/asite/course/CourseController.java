package me.asite.course;

import lombok.RequiredArgsConstructor;
import me.asite.course.dto.CourseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/course", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;


    @GetMapping
    public ResponseEntity query_Courses(@RequestBody CourseSearch courseSearch,
                                        Pageable pageable,
                                        PagedResourcesAssembler<Course> assembler,
                                        Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Page<Course> coursesPage = courseService.findCourses(courseSearch, pageable);
        PagedResources<CourseResouce> courseResouces = assembler.toResource(coursesPage, CourseResouce::new);
        courseResouces.add(new Link("/docs/index.html#resource-course-list").withRel("profile"));

        return ResponseEntity.ok(courseResouces);
    }

}
