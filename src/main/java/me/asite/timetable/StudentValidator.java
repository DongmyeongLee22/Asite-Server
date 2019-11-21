package me.asite.timetable;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component
public class StudentValidator {

    /**
     * DTO의 StudentId과 로그인된 회원의 StudentId가 다를 경우 에러를 추가한다.
     */
    public void validateStudentId(Long parameterStudentId, Long loginedStudentId, Errors errors) {
        if (!parameterStudentId.equals(loginedStudentId)) {
            errors.rejectValue("studenrId", "wrongValue", "회원아이디가 맞지 않습니다.");
        }
    }
}
