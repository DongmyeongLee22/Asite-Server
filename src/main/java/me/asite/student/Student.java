package me.asite.student;

import lombok.*;
import me.asite.timetable.Timetable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
@Getter
@Setter
public class Student {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Column(unique = true)
    private String studentNumber;

    private String password;

    private String name;

    private String major;

    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<StudentRole> roles;

    @OneToMany(mappedBy = "student")
    private List<Timetable> timetableList = new ArrayList<>();

    public void encodingPassword(String encode) {
        this.password = encode;
    }
}
