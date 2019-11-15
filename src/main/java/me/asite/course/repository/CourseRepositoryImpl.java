package me.asite.course.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import me.asite.course.Course;
import me.asite.course.dto.CourseSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.asite.course.QCourse.course;


public class CourseRepositoryImpl extends QuerydslRepositorySupport implements CourseRepositoryCustom {


    public CourseRepositoryImpl() {
        super(Course.class);
    }

    @Override
    public List<Course> findCourses(CourseSearch courseSearch) {
        return from(course)
                .where(
                        eqYear(courseSearch.getYear()),
                        eqSemester(courseSearch.getSemester()),
                        eqGrade(courseSearch.getGrade()),
                        eqMajor(courseSearch.getMajor()))
                .fetch();

    }

    @Override
    public Page<Course> findCourses(CourseSearch courseSearch, Pageable pageable) {
        QueryResults<Course> courseQueryResults = from(course)
                .where(
                        eqYear(courseSearch.getYear()),
                        eqSemester(courseSearch.getSemester()),
                        eqGrade(courseSearch.getGrade()),
                        eqMajor(courseSearch.getMajor()))
                .fetchResults();
        return new PageImpl<>(courseQueryResults.getResults(), pageable, courseQueryResults.getTotal());
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
