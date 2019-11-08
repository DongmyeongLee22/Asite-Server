package me.asite.api.response;

import lombok.Data;

@Data
public class LoginResponseDto {

    private boolean success;
    private Long studentId;
    private String name;

    public LoginResponseDto(boolean success, Long studentId, String name) {
        this.success = success;
        this.studentId = studentId;
        this.name = name;
    }
}
