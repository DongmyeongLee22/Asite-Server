package me.asite.service;

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
    public void 강의추가() throws Exception{
        //given
        Course course = Course.builder()
                .title("제어공학")
                .year(2019)
                .term(1)
                .credit("3학점")
                .build();
        courseService.courseAdd(course);
        //when
        em.flush(); em.clear();
        Course findCourse = courseService.findOne(course.getId());

        //then
        Assert.assertEquals("제어공학", findCourse.getTitle());
        Assert.assertEquals(2019, findCourse.getYear());

        }


}