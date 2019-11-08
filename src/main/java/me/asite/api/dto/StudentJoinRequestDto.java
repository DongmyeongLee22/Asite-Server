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
    private String studentNumber;
    private String password;
    private String major;
    private String email;

    public Student toEntity() {

        return Student.builder()
                .name(name)
                .studentNumber(studentNumber)
                .password(password)
                .major(major)
                .email(email)
                .build();

    }
}
