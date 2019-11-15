package me.asite.course;

import me.asite.course.dto.CourseAddRequestDto;
import me.asite.course.dto.CourseSearch;
import me.asite.course.repository.CourseRepository;
import me.asite.exception.CannotFindByIDException;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseServiceTest {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @After
    public void celanUp() {
        courseRepository.deleteAll();
    }

    @Test
    public void courseAdd() throws Exception {
        //given
        Course course = createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "디자인패턴");

        //then
        Course findCourse = courseService.findOne(course.getId());
        assertThat(findCourse.getId()).isEqualTo(course.getId());
        assertThat(findCourse.getClassNumber()).isEqualTo(course.getClassNumber());
        assertThat(findCourse.getTime()).isEqualTo(course.getTime());
        assertThat(findCourse.getTitle()).isEqualTo(course.getTitle());

    }

    @Test(expected = CannotFindByIDException.class)
    public void findOneCourseWithEception() throws Exception {
        //when
        courseService.findOne(3039L);
        //then
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void findCourses() throws Exception {
        //given
        createCourseAndAdd(2019, 1, "2학년", "전자공학과", "전자공학");
        createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "디자인패턴");
        createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "알고리즘");
        createCourseAndAdd(2019, 1, "1학년", "전자공학과", "전자회로");

        CourseSearch courseSearch = new CourseSearch(2019, 1, "3학년", "컴퓨터공학과");

        //when
        List<Course> courses = courseService.findCourses(courseSearch);

        //then
        assertThat(courses.size()).isEqualTo(2);
        assertThat(courses.get(0).getMajor()).isEqualTo(courseSearch.getMajor());
        assertThat(courses.get(0).getGrade()).isEqualTo(courseSearch.getGrade());

        //when
        courseSearch = new CourseSearch(2019, 1, "2학년", "전자공학과");
        courses = courseService.findCourses(courseSearch);

        //then
        assertThat(courses.size()).isEqualTo(1);
        assertThat(courses.get(0).getMajor()).isEqualTo(courseSearch.getMajor());
        assertThat(courses.get(0).getGrade()).isEqualTo(courseSearch.getGrade());

        //when
        courseSearch = new CourseSearch(2019, 1, "모든 학년", "");
        courses = courseService.findCourses(courseSearch);

        //then
        assertThat(courses.size()).isEqualTo(4);

    }


    private Course createCourseAndAdd(int year, int semester, String grade, String major, String title) {
        CourseAddRequestDto courseDto = CourseAddRequestDto.builder()
                .classNumber(103)
                .year(year)
                .semester(semester)
                .subject("전공필수")
                .major(major)
                .grade(grade)
                .title(title)
                .credit("3학점")
                .location("A13-2126")
                .professor("홍길동")
                .time("월:[6](14:00-14:50) 수:[2][3](10:00-11:50)")
                .build();
        return courseService.courseAdd(courseDto);
    }


}