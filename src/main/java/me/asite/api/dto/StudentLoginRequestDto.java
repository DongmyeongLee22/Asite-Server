package me.asite.api.dto;

import lombok.Data;

@Data
public class StudentLoginRequestDto {

    private String studentId;
    private String password;

}
