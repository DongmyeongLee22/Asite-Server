package me.asite.student;

import lombok.RequiredArgsConstructor;
import me.asite.common.IsSuccessReponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentApiController {

    private final StudentService studentService;

    @PostMapping("/student/join")
    public IsSuccessReponse joinStudent(@RequestBody StudentJoinRequestDto dto) {
            return studentService.join(dto.toEntity());
    }

    @PostMapping("/student/join/validate")
    public IsSuccessReponse validateJoin(@RequestBody JoinValidateRequest dto) {
        return new IsSuccessReponse(studentService.vailidateJoin(dto.getStudentNumber()));
    }
    @PostMapping("/student/login")
    public LoginResponseDto login(@RequestBody StudentLoginRequestDto dto) {
        return studentService.login(dto);
    }
}
