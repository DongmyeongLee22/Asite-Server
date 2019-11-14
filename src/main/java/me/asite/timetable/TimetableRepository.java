package me.asite.timetable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimetableRepository extends JpaRepository<Timetable, Long>{

    @Query("select t from Timetable t join fetch t.course c where t.student.id like :studentId")
    List<Timetable> findAllWithCourseByStudentId(@Param("studentId") Long studentId);
}
