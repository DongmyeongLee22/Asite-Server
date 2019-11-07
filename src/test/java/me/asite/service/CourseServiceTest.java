package me.asite.service;

import me.asite.api.controller.CourseSearch;
import me.asite.domain.Course;
import me.asite.repository.CourseRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager em;


    @Test
    public void 강의추가() throws Exception {
        //given
        Course course = createCourse("제어공학", 2019, 1, "정보통신공학", "3학년");
        courseService.courseAdd(course);
        //when
        em.flush();
        em.clear();
        Course findCourse = courseService.findOne(course.getId());

        //then
        Assert.assertEquals("제어공학", findCourse.getTitle());
        Assert.assertEquals(2019, findCourse.getYear());

    }

    @Test
    public void 강의목록조회() throws Exception{
        //given
        Course course1 = createCourse("알고리즘", 2019, 1, "컴퓨터공학", "4학년");
        Course course2 = createCourse("제어공학", 2019, 1, "정보통신공학", "3학년");
        Course course3 = createCourse("전자회로", 2019, 1, "전자공학", "2학년");

        CourseSearch courseSearch1 = new CourseSearch(2019, 1, "4학년", "컴퓨터공학");
        CourseSearch courseSearch2 = new CourseSearch(0, 0, "전체 학년", "");

        courseService.courseAdd(course1);
        courseService.courseAdd(course2);
        courseService.courseAdd(course3);

        em.flush(); em.clear();
        //when
        List<Course> courses1 = courseService.findCourses(courseSearch1);
        List<Course> courses2 = courseService.findCourses(courseSearch2);

        //then
        Assert.assertEquals("알고리즘", courses1.get(0).getTitle());
        Assert.assertEquals(1, courses1.size());
        Assert.assertEquals(3,courses2.size());
        }


    private Course createCourse(String title, int year, int term, String major, String grade) {
        return Course.builder()
                .title(title)
                .year(year)
                .term(term)
                .grade(grade)
                .major(major)
                .build();
    }


}