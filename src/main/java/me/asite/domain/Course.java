package me.asite.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Course {

    @Id
    @GeneratedValue
    @Column(name = "course_id")
    private Long id;

    private int devide;

    private int year;

    private int term;

    private String area;

    private String major;

    private String grade;

    private String title;

    private String credit;

    private String room;

    private String professor;

    private String time;

    @Builder
    public Course(int devide, int year, int term, String area, String major, String grade, String title, String credit, String room, String professor, String time) {
        this.devide = devide;
        this.year = year;
        this.term = term;
        this.area = area;
        this.major = major;
        this.grade = grade;
        this.title = title;
        this.credit = credit;
        this.room = room;
        this.professor = professor;
        this.time = time;
    }
}
