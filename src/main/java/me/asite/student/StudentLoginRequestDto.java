package me.asite.student;

import lombok.Data;

@Data
public class StudentLoginRequestDto {

    private String studentNumber;
    private String password;

}
