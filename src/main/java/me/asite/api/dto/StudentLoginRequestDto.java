package me.asite.api.dto;

import lombok.Data;

@Data
public class StudentLoginRequestDto {

    private String sudentId;
    private String password;

}
