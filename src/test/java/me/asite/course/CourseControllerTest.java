package me.asite.course;

import me.asite.common.AppProperties;
import me.asite.common.BaseControllerTest;
import me.asite.common.TestDescription;
import me.asite.course.dto.CourseAddRequestDto;
import me.asite.course.dto.CourseSearch;
import me.asite.course.repository.CourseRepository;
import me.asite.student.StudentRole;
import me.asite.student.StudentService;
import me.asite.student.dto.StudentJoinRequestDto;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CourseControllerTest extends BaseControllerTest {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentService studentService;

    @Autowired
    AppProperties appProperties;

    @After
    public void cleanUp() {
        courseRepository.deleteAll();
    }

    @Test
    @TestDescription("조건에 맞는 강의를 조회")
    public void query_Courses() throws Exception {
        //given
        createCourseAndAdd(2019, 1, "2학년", "전자공학과", "전자공학");
        createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "디자인패턴");
        createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "알고리즘");
        createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "데이터통신");
        createCourseAndAdd(2019, 1, "3학년", "컴퓨터공학과", "자료구조");
        createCourseAndAdd(2019, 1, "1학년", "전자공학과", "전자회로");

        CourseSearch courseSearch = new CourseSearch(2019, 1, "3학년", "컴퓨터공학과");

        //when && then
        this.mockMvc.perform(get("/api/course")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + findAccessToken())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(courseSearch))
                .param("page", "1")
                .param("size", "2")
                .param("sort", "title,DESC")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("query-courses",
                        links(
                                linkWithRel("profile").description("RestDocs링크"),
                                linkWithRel("first").description("첫번째 페이지 링크"),
                                linkWithRel("prev").description("이전 페이지 링크"),
                                linkWithRel("self").description("자신의 페이지 링크"),
                                linkWithRel("last").description("마지막 페이지 링크")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON"),
                                headerWithName(HttpHeaders.AUTHORIZATION).description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("year").description("강의 년도"),
                                fieldWithPath("semester").description("강의 학기"),
                                fieldWithPath("grade").description("강의 학년"),
                                fieldWithPath("major").description("강의 전공")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("JSON")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.courseList[0].id").description("강의 ID"),
                                fieldWithPath("_embedded.courseList[0].classNumber").description("강의 분반"),
                                fieldWithPath("_embedded.courseList[0].year").description("강의 년도"),
                                fieldWithPath("_embedded.courseList[0].semester").description("강의 학기"),
                                fieldWithPath("_embedded.courseList[0].subject").description("강의 분류"),
                                fieldWithPath("_embedded.courseList[0].major").description("강의 전공"),
                                fieldWithPath("_embedded.courseList[0].grade").description("강의 학년"),
                                fieldWithPath("_embedded.courseList[0].title").description("강의 제목"),
                                fieldWithPath("_embedded.courseList[0].credit").description("강의 학점"),
                                fieldWithPath("_embedded.courseList[0].location").description("강의실 위치"),
                                fieldWithPath("_embedded.courseList[0].professor").description("강의 담당 교수"),
                                fieldWithPath("_embedded.courseList[0].time").description("강의 시간"),
                                fieldWithPath("_embedded.courseList[0]._links.self.href").description("강의 링크"),
                                fieldWithPath("_links.first.href").description("첫번째 페이지 링크"),
                                fieldWithPath("_links.prev.href").description("이전 페이지 링크"),
                                fieldWithPath("_links.self.href").description("자신의 페이지 링크"),
                                fieldWithPath("_links.last.href").description("마지막 페이지 링크"),
                                fieldWithPath("_links.profile.href").description("Rest Docs 리으"),
                                fieldWithPath("page.size").description("페이지 크기"),
                                fieldWithPath("page.totalElements").description("강의 갯수"),
                                fieldWithPath("page.totalPages").description("페이지 갯수"),
                                fieldWithPath("page.number").description("페이지 위치")

                        )
                ));

    }

    private String findAccessToken() throws Exception {
        String studentNumber = "201412341";
        String password = "password";
        StudentJoinRequestDto studentDto = getStudentDto(studentNumber, password);

        this.studentService.join(studentDto);

        return getAccessToken(studentNumber, password);
    }

    private String findAccessToken(String username, String password) throws Exception {
        return getAccessToken(username, password);
    }

    private String getAccessToken(String username, String password) throws Exception {
        ResultActions perform = this.mockMvc.perform(post("/oauth/token")
                .with(httpBasic(appProperties.getClientId(), appProperties.getClientSecret()))
                .param("username", username)
                .param("password", password)
                .param("grant_type", "password")
        );
        String contentAsString = perform.andReturn().getResponse().getContentAsString();
        Jackson2JsonParser parser = new Jackson2JsonParser();
        return parser.parseMap(contentAsString).get("access_token").toString();
    }


    private void createCourseAndAdd(int year, int semester, String grade, String major, String title) {
        CourseAddRequestDto courseDto = CourseAddRequestDto.builder()
                .classNumber(103)
                .year(year)
                .semester(semester)
                .subject("전공필수")
                .major(major)
                .grade(grade)
                .title(title)
                .credit("3학점")
                .location("A13-2126")
                .professor("홍길동")
                .time("월:[6](14:00-14:50) 수:[2][3](10:00-11:50)")
                .build();
        courseService.courseAdd(courseDto);
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