package me.asite.config;

import com.google.common.net.HttpHeaders;
import me.asite.common.AppProperties;
import me.asite.common.BaseControllerTest;
import me.asite.common.TestDescription;
import me.asite.student.StudentRepository;
import me.asite.student.StudentRole;
import me.asite.student.StudentService;
import me.asite.student.dto.StudentJoinRequestDto;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthServerConfigTest extends BaseControllerTest {

    @Autowired
    StudentService studentService;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    AppProperties appProperties;

    @After
    public void cleanUp() {
        studentRepository.deleteAll();
    }

    @Test
    @TestDescription("인증 토큰 받기")
    public void getAouthToken() throws Exception {
        //given
        String studentNumber = "201412341";
        String password = "password";
        StudentJoinRequestDto studentDto = getStudentDto(studentNumber, password);

        this.studentService.join(studentDto);

        //when && then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", studentNumber)
                .param("password", password)
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-aouthToken",
                        requestHeaders(
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Authorizaiton: Basic")
                        ),
                        requestParameters(
                                parameterWithName("username").description("사용자 ID"),
                                parameterWithName("password").description("사용자 password"),
                                parameterWithName("grant_type").description("Grant_Type")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CACHE_CONTROL).description("Cache-Control"),
                                headerWithName(HttpHeaders.PRAGMA).description("Pragma"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type"),
                                headerWithName(HttpHeaders.X_CONTENT_TYPE_OPTIONS).description("X-Content-Type-Options"),
                                headerWithName(HttpHeaders.X_XSS_PROTECTION).description("X-XSS-Protection"),
                                headerWithName(HttpHeaders.X_FRAME_OPTIONS).description("X-Frame-Options")
                        ),
                        responseFields(
                                fieldWithPath("access_token").description("Access_Token"),
                                fieldWithPath("token_type").description("Token_Type"),
                                fieldWithPath("refresh_token").description("Refresh_Token"),
                                fieldWithPath("expires_in").description("Expires_in"),
                                fieldWithPath("scope").description("Scope")
                        )
                ));
    }

    @Test
    @TestDescription("회원이 아닌데 토큰을 받으려고 할때")
    public void getAouthToekn_BadRequest() throws Exception {
        //when && then
        this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", "123213")
                .param("password", "1pqdqw")
                .param("grant_type", "password")
        )
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }


    private StudentJoinRequestDto getStudentDto(String studentNumber, String password) {
        return StudentJoinRequestDto.builder()
                .studentNumber(studentNumber)
                .password(password)
                .email("test@email.com")
                .major("컴퓨터 공학과")
                .name("홍길동")
                .roles(Collections.singleton(StudentRole.USER))
                .build();
    }


}