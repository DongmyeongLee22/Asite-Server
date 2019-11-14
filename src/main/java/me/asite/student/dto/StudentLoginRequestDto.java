package me.asite.student.dto;

import lombok.Data;

@Data
public class StudentLoginRequestDto {

    private String studentNumber;
    private String password;

}
