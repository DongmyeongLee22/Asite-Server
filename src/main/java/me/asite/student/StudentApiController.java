package me.asite.student;

import lombok.RequiredArgsConstructor;
import me.asite.student.dto.StudentJoinRequestDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@RequestMapping(value = "/api/student", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class StudentApiController {

    private final StudentService studentService;

    @PostMapping("/join")
    public ResponseEntity joinStudent(@RequestBody @Valid StudentJoinRequestDto joinRequestDto,
                                      Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Student student = studentService.join(joinRequestDto);
        StudentResource studentResource = new StudentResource(student);
        studentResource.add(linkTo(StudentApiController.class).slash("login").withRel("login"));
        studentResource.add(new Link("/docs/index.html#resources-student-join").withRel("profile"));

        return ResponseEntity.ok().body(studentResource);

    }

}
