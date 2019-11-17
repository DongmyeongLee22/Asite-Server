package me.asite.student;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentAdapter extends User {


    private Student student;

    public StudentAdapter(Student student) {
        super(student.getStudentNumber(), student.getPassword(), authorities(student.getRoles()));
        this.student = student;
    }

    private static Collection<? extends GrantedAuthority> authorities(Set<StudentRole> roles) {
        return roles.stream()
                .map(r -> new SimpleGrantedAuthority("ROLS_" + r.name()))
                .collect(Collectors.toSet());
    }

    public Student getStudent() {
        return student;
    }
}
