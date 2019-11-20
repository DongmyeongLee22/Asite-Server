package me.asite.student.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.asite.student.StudentRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentJoinRequestDto {

    @NotEmpty
    private String name;
    @NotEmpty
    private String studentNumber;
    @NotEmpty
    private String password;
    @NotEmpty
    private String major;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private Set<StudentRole> roles;

}
