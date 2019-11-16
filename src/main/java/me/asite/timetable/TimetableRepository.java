package me.asite.timetable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TimetableRepository extends JpaRepository<Timetable, Long>{

    @Query("select t from Timetable t join fetch t.course c where t.student.id like :studentId")
    List<Timetable> findAllWithCourseByStudentId(@Param("studentId") Long studentId);

    @Query("select t from Timetable t join fetch t.course c join fetch t.student s where t.id like :timetableId")
    Optional<Timetable> findByIdWithStudentAndCourse(@Param("timetableId") Long timetableId);
}
