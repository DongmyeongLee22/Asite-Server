package me.asite.student;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

public class StudentResource extends Resource<Student> {

    public StudentResource(Student content, Link... links) {
        super(content, links);
    }
}
