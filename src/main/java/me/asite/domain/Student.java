package me.asite.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
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

    private String studentNumber;

    private String password;

    private String name;

    private String major;

    private String email;
    @OneToMany(mappedBy = "student")
    private List<ScheduleAttendance> scheduleAttendanceList = new ArrayList<>();

    @Builder
    public Student(String studentNumber, String password, String name, String major, String email) {
        this.studentNumber = studentNumber;
        this.password = password;
        this.name = name;
        this.major = major;
        this.email = email;
    }
}
