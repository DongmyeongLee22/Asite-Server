package me.asite.course;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
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

}
