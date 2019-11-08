package me.asite.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.asite.api.controller.CourseSearch;
import me.asite.domain.Course;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static me.asite.domain.QCourse.course;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public void save(Course course) {
        em.persist(course);
    }

    public Course findOne(Long courseId) {
        return em.find(Course.class, courseId);
    }

    public List<Course> findAll() {
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }


    public List<Course> findCourses(CourseSearch courseSearch) {
        return queryFactory.selectFrom(course)
                .where(eqYear(courseSearch.getYear()),
                        eqSemester(courseSearch.getSemester()),
                        eqGrade(courseSearch.getGrade()),
                        eqMajor(courseSearch.getMajor()))
                .fetch();
    }

    private BooleanExpression eqYear(int year) {
        if (year == 0) {
            return null;
        }
        return course.year.eq(year);
    }

    private BooleanExpression eqSemester(int semester) {
        if (semester == 0) {
            return null;
        }
        return course.semester.eq(semester);
    }

    private BooleanExpression eqGrade(String grade) {
        if (grade.equals("모든 학년")) {
            return null;
        }
        return course.grade.eq(grade);
    }

    private BooleanExpression eqMajor(String major) {
        if (StringUtils.isEmpty(major)) {
            return null;
        }
        return course.major.eq(major);
    }
}
