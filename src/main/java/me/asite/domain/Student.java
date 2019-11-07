package me.asite.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String studentId;

    private String password;

    private String name;

    private String major;

    @OneToMany(mappedBy = "student")
    private List<Schedule> scheduleList;

    @Builder
    public Student(String studentId, String password, String name, String major) {
        this.studentId = studentId;
        this.password = password;
        this.name = name;
        this.major = major;
    }
}
