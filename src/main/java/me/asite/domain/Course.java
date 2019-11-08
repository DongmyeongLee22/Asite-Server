package me.asite.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private int classNumber;
    private int year;
    private int semester;
    private String subject;
    private String major;
    private String grade;
    private String title;
    private String credit;
    private String location;
    private String professor;
    private String time;

    @Builder
    public Course(int classNumber, int year, int semester, String subject, String major, String grade, String title, String credit, String location, String professor, String time) {
        this.classNumber = classNumber;
        this.year = year;
        this.semester = semester;
        this.subject = subject;
        this.major = major;
        this.grade = grade;
        this.title = title;
        this.credit = credit;
        this.location = location;
        this.professor = professor;
        this.time = time;
    }
}
