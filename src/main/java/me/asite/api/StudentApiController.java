package me.asite.api;

import lombok.RequiredArgsConstructor;
import me.asite.service.StudentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentApiController {

    private final StudentService studentService;

    @PostMapping("/student/join")
    public void joinStudent(@RequestBody StudentJoinRequestDto dto){
        studentService.join(dto.toEntity());
    }
}
