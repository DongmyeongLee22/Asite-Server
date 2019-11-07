package me.asite.repository;

import lombok.RequiredArgsConstructor;
import me.asite.domain.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CourseRepository {

    private final EntityManager em;

    public void save(Course course){
        em.persist(course);
    }

    public Course findOne(Long courseId){
        return em.find(Course.class, courseId);
    }

    public List<Course> findAll(){
        return em.createQuery("select c from Course c", Course.class).getResultList();
    }



}
