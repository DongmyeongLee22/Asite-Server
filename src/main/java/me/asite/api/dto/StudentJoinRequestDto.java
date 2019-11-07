package me.asite.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.asite.domain.Student;

@Getter
@Setter
@NoArgsConstructor
public class StudentJoinRequestDto {

    private String name;
    private String studentId;
    private String password;
    private String major;

    public Student toEntity() {

        return Student.builder()
                .name(name)
                .studentId(studentId)
                .password(password)
                .major(major)
                .build();

    }
}
