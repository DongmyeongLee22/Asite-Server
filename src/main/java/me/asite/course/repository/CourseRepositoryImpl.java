package me.asite.course.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.asite.course.Course;
import me.asite.course.dto.CourseSearch;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.asite.course.QCourse.course;


@RequiredArgsConstructor
@Repository
public class CourseRepositoryImpl implements CourseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Course> findCourses(CourseSearch courseSearch) {
        List<Course> fetch = queryFactory.selectFrom(course)
                .where(eqYear(courseSearch.getYear()),
                        eqSemester(courseSearch.getSemester()),
                        eqGrade(courseSearch.getGrade()),
                        eqMajor(courseSearch.getMajor()))
                .fetch();

        return fetch;
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
