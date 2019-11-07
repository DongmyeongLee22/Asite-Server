package me.asite.api.controller;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.StudentJoinRequestDto;
import me.asite.domain.Student;
import me.asite.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentApiController {

    private final StudentService studentService;

    @PostMapping("/student/join")
    public Student joinStudent(@RequestBody StudentJoinRequestDto dto) {
        return studentService.join(dto.toEntity());
    }
}
