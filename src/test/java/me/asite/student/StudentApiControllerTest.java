package me.asite.student;

import me.asite.common.BaseControllerTest;
import me.asite.student.dto.StudentJoinRequestDto;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class StudentApiControllerTest extends BaseControllerTest {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @After
    public void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    public void 회원가입() throws Exception {
        //given
        StudentJoinRequestDto studentDto = getStudentDto();

        //when
        this.mockMvc.perform(post("/api/student/join")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(objectMapper.writeValueAsString(studentDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("join-student",
                        links(
                                linkWithRel("login").description("로그인 링크"),
                                linkWithRel("profile").description("RestDocs 링크")

                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("ACCEPT HEADER: HAL_JSON"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("CONTENT_TYPE: APPLICAITON_JSON_UTF8")
                        ),
                        requestFields(
                                fieldWithPath("studentNumber").description("회원의 학번"),
                                fieldWithPath("password").description("회원의 비밀번호"),
                                fieldWithPath("name").description("회원의 이름"),
                                fieldWithPath("email").description("회원의 이메일"),
                                fieldWithPath("major").description("회원의 전공"),
                                fieldWithPath("roles").description("회원의 권한")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("CONTENT_TYPE: APPLICAITON_JSON_UTF8")
                        ),
                        responseFields(
                                fieldWithPath("id").description("회원의 PRIMARY KEY ID"),
                                fieldWithPath("studentNumber").description("회원의 학번"),
                                fieldWithPath("password").description("회원의 비밀번호"),
                                fieldWithPath("name").description("회원의 이름"),
                                fieldWithPath("email").description("회원의 이메일"),
                                fieldWithPath("major").description("회원의 전공"),
                                fieldWithPath("roles").description("회원의 권한"),
                                fieldWithPath("timetableList").description("회원의 시간표"),
                                fieldWithPath("_links.login.href").description("로그인 링크"),
                                fieldWithPath("_links.profile.href").description("Rest Docs 링크")
                        )
                ));

        //then
    }

    private StudentJoinRequestDto getStudentDto() {
        return StudentJoinRequestDto.builder()
                .studentNumber("201412341")
                .password("password")
                .email("test@email.com")
                .major("컴퓨터 공학과")
                .name("홍길동")
                .roles(Collections.singleton(StudentRole.USER))
                .build();
    }


}