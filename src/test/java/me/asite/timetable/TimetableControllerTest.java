package me.asite.timetable;

import me.asite.attendance.AttendanceCheckRequestDto;
import me.asite.common.AppProperties;
import me.asite.common.BaseControllerTest;
import me.asite.common.TestDescription;
import me.asite.course.Course;
import me.asite.course.repository.CourseRepository;
import me.asite.student.Student;
import me.asite.student.StudentRepository;
import me.asite.student.StudentRole;
import me.asite.student.StudentService;
import me.asite.student.dto.StudentJoinRequestDto;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.common.util.Jackson2JsonParser;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TimetableControllerTest extends BaseControllerTest {

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    TimetableRepository timetableRepository;

    @Autowired
    TimetableService timetableService;

    @Autowired
    AppProperties appProperties;

    @Autowired
    StudentService studentService;

    @After
    public void cleanUp() {
        timetableRepository.deleteAll();
        studentRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    @TestDescription("시간표를 추가하는 테스트")
    public void addTimetable() throws Exception {
        //given
        String studentNumber = "201412345";
        String password = "password";
        Student student = getStudentAndJoin(studentNumber, password);
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        TimetableAddRequestDto dto = new TimetableAddRequestDto(student.getId(), course.getId());

        //when && then
        this.mockMvc.perform(post("/api/timetable/add")
                .header(AUTHORIZATION, getAccessToken(studentNumber, password))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("add-timetable",
                        links(
                                linkWithRel("profile").description("RestDocs링크"),
                                linkWithRel("self").description("self")
                        ),
                        requestHeaders(
                                headerWithName(CONTENT_TYPE).description("JSON"),
                                headerWithName(AUTHORIZATION).description("Bearer Token")
                        ),
                        requestFields(
                                fieldWithPath("studentId").description("학생 외래 키"),
                                fieldWithPath("courseId").description("강의 외래 키")
                        ),
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("JSON")
                        ),
                        responseFields(
                                fieldWithPath("id").description("시간표 아이디"),
                                fieldWithPath("attendanceCount").description("정상 출석 횟수"),
                                fieldWithPath("latelessCount").description("지각 횟수"),
                                fieldWithPath("absentCount").description("결석 횟수"),
                                fieldWithPath("earlyEnd").description("빠른 종료 횟수"),
                                fieldWithPath("student.id").description("학생 아이디"),
                                fieldWithPath("course.id").description("강의 ID"),
                                fieldWithPath("course.classNumber").description("강의 분반"),
                                fieldWithPath("course.year").description("강의 년도"),
                                fieldWithPath("course.semester").description("강의 학기"),
                                fieldWithPath("course.subject").description("강의 분류"),
                                fieldWithPath("course.major").description("강의 전공"),
                                fieldWithPath("course.grade").description("강의 학년"),
                                fieldWithPath("course.title").description("강의 제목"),
                                fieldWithPath("course.credit").description("강의 학점"),
                                fieldWithPath("course.location").description("강의실 위치"),
                                fieldWithPath("course.professor").description("강의 담당 교수"),
                                fieldWithPath("course.time").description("강의 시간"),
                                fieldWithPath("attendanceList").description("일별 출석 세부 사항"),
                                fieldWithPath("_links.self.href").description("self"),
                                fieldWithPath("_links.profile.href").description("Rest Docs 링크")
                        )
                ));
    }

    @Test
    @TestDescription("강의 ID가 없을때 시간표를 추가할 경우")
    public void addTimetable_BadRequest() throws Exception {
        //given
        String studentNumber = "201412345";
        String password = "password";
        Student student = getStudentAndJoin(studentNumber, password);
        TimetableAddRequestDto dto = new TimetableAddRequestDto();
        dto.setStudentId(student.getId());

        // when && then
        this.mockMvc.perform(post("/api/timetable/add")
                .header(AUTHORIZATION, getAccessToken(studentNumber, password))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(dto))
        )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("출석체크를 하는 테스트")
    public void attendanceCheck() throws Exception {
        //given
        String studentNumber = "201412345";
        String password = "password";
        Student student = getStudentAndJoin(studentNumber, password);
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Timetable addedtimetable = timetableService.addTimetable(student.getId(), course.getId());
        AttendanceCheckRequestDto attendanceDto = createAddtendanceDto();

        //when && then
        this.mockMvc.perform(put("/api/timetable/{id}", addedtimetable.getId())
                .header(AUTHORIZATION, getAccessToken(studentNumber, password))
                .param("studentId", student.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(attendanceDto))
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("attendance-check",
                        links(
                                linkWithRel("profile").description("Rest Docs 문서"),
                                linkWithRel("self").description("자신의 링크")
                        ),
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("Bearer Token"),
                                headerWithName(CONTENT_TYPE).description("JSON")

                        ),
                        requestFields(
                                fieldWithPath("attendanceDate").description("출석 일자"),
                                fieldWithPath("startTime").description("출석 시작 시간"),
                                fieldWithPath("endTime").description("출석 종료 시간")
                        ),
                        requestParameters(
                                parameterWithName("studentId").description("학생 아이디")
                        ),
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("HAL JSON 타입")
                        ),
                        responseFields(
                                fieldWithPath("id").description("시간표 아이디"),
                                fieldWithPath("student.id").description("학생 아이디"),
                                fieldWithPath("course.id").description("강의 ID"),
                                fieldWithPath("course.classNumber").description("강의 분반"),
                                fieldWithPath("course.year").description("강의 년도"),
                                fieldWithPath("course.semester").description("강의 학기"),
                                fieldWithPath("course.subject").description("강의 분류"),
                                fieldWithPath("course.major").description("강의 전공"),
                                fieldWithPath("course.grade").description("강의 학년"),
                                fieldWithPath("course.title").description("강의 제목"),
                                fieldWithPath("course.credit").description("강의 학점"),
                                fieldWithPath("course.location").description("강의실 위치"),
                                fieldWithPath("course.professor").description("강의 담당 교수"),
                                fieldWithPath("course.time").description("강의 시간"),
                                fieldWithPath("attendanceList[0].attendanceDate").description("출석 일자"),
                                fieldWithPath("attendanceList[0].startTime").description("출석 시작 시간"),
                                fieldWithPath("attendanceList[0].endTime").description("출석 종료 시간"),
                                fieldWithPath("attendanceList[0].attendanceState").description("출석 상태"),
                                fieldWithPath("attendanceList[0].attendanceEndState").description("출석 종료 상태"),
                                fieldWithPath("attendanceCount").description("정상 출석 횟수"),
                                fieldWithPath("latelessCount").description("지각 횟수"),
                                fieldWithPath("absentCount").description("결석 횟수"),
                                fieldWithPath("earlyEnd").description("빠른 종료 횟수"),
                                fieldWithPath("_links.self.href").description("self"),
                                fieldWithPath("_links.profile.href").description("Rest Docs 링크")
                        )
                ));
    }

    @Test
    @TestDescription("출석체크를 하는데 출석 시간이 없을때")
    public void attendanceCheck_BadRequest() throws Exception {
        //given
        String studentNumber = "201412345";
        String password = "password";
        Student student = getStudentAndJoin(studentNumber, password);
        Course course = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Timetable addedtimetable = timetableService.addTimetable(student.getId(), course.getId());
        AttendanceCheckRequestDto attendanceDto = AttendanceCheckRequestDto.builder().build();

        //when && then
        this.mockMvc.perform(put("/api/timetable/{id}", addedtimetable.getId())
                .header(AUTHORIZATION, getAccessToken(studentNumber, password))
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(attendanceDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @TestDescription("학생의 시간표를 조회하는 테스트")
    public void query_TimetableByStudentId() throws Exception {
        //given
        String studentNumber = "201412345";
        String password = "password";
        Student student = getStudentAndJoin(studentNumber, password);
        Course course1 = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Course course2 = createCourseAndAdd("4학년", "컴퓨터공학과", "자료구조");
        Course course3 = createCourseAndAdd("4학년", "컴퓨터공학과", "디자인패턴");
        timetableService.addTimetable(student.getId(), course1.getId());
        timetableService.addTimetable(student.getId(), course2.getId());
        timetableService.addTimetable(student.getId(), course3.getId());

        //when && then
        this.mockMvc.perform(get("/api/timetable/queryTimetable")
                .header(AUTHORIZATION, getAccessToken(studentNumber, password))
                .param("studentId", student.getId().toString()))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("query-timetable",
                        links(
                                linkWithRel("profile").description("Rest Docs 문서"),
                                linkWithRel("self").description("자신의 링크")
                        ),
                        requestHeaders(
                                headerWithName(AUTHORIZATION).description("Bearer Token")

                        ),
                        requestParameters(
                                parameterWithName("studentId").description("학생의 아이디")
                        ),
                        responseHeaders(
                                headerWithName(CONTENT_TYPE).description("HAL JSON 타입")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.timetableList[0].id").description("시간표 아이디"),
                                fieldWithPath("_embedded.timetableList[0].student.id").description("학생 아이디"),
                                fieldWithPath("_embedded.timetableList[0].course.id").description("강의 ID"),
                                fieldWithPath("_embedded.timetableList[0].course.classNumber").description("강의 분반"),
                                fieldWithPath("_embedded.timetableList[0].course.year").description("강의 년도"),
                                fieldWithPath("_embedded.timetableList[0].course.semester").description("강의 학기"),
                                fieldWithPath("_embedded.timetableList[0].course.subject").description("강의 분류"),
                                fieldWithPath("_embedded.timetableList[0].course.major").description("강의 전공"),
                                fieldWithPath("_embedded.timetableList[0].course.grade").description("강의 학년"),
                                fieldWithPath("_embedded.timetableList[0].course.title").description("강의 제목"),
                                fieldWithPath("_embedded.timetableList[0].course.credit").description("강의 학점"),
                                fieldWithPath("_embedded.timetableList[0].course.location").description("강의실 위치"),
                                fieldWithPath("_embedded.timetableList[0].course.professor").description("강의 담당 교수"),
                                fieldWithPath("_embedded.timetableList[0].course.time").description("강의 시간"),
                                fieldWithPath("_embedded.timetableList[0].attendanceList[0]").description("출석 세부 사항"),
                                fieldWithPath("_embedded.timetableList[0].attendanceCount").description("정상 출석 횟수"),
                                fieldWithPath("_embedded.timetableList[0].latelessCount").description("지각 횟수"),
                                fieldWithPath("_embedded.timetableList[0].absentCount").description("결석 횟수"),
                                fieldWithPath("_embedded.timetableList[0].earlyEnd").description("빠른 종료 횟수"),
                                fieldWithPath("_embedded.timetableList[0]._links.self.href").description("빠른 종료 횟수"),
                                fieldWithPath("_links.self.href").description("self"),
                                fieldWithPath("_links.profile.href").description("Rest Docs 링크")
                        )
                ));
    }

    @Test
    public void delete_Timetable() throws Exception {
        //given
        String studentNumber = "201412345";
        String password = "password";
        Student student = getStudentAndJoin(studentNumber, password);
        Course course1 = createCourseAndAdd("4학년", "컴퓨터공학과", "알고리즘");
        Timetable addedTimetable = timetableService.addTimetable(student.getId(), course1.getId());

        //when && then
        this.mockMvc.perform(delete("/{id}", addedTimetable.getId())
                .header(AUTHORIZATION, getAccessToken(studentNumber, password)))
                .andDo(print())
                .andExpect(status().isOk());

    }

    private AttendanceCheckRequestDto createAddtendanceDto() {
        return AttendanceCheckRequestDto.builder()
                .attendanceDate("11-16")
                .startTime("10:00")
                .endTime("11:00")
                .build();
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
        return "Bearer " + parser.parseMap(contentAsString).get("access_token").toString();
    }

    private Course createCourseAndAdd(String grade, String major, String title) {
        Course course = Course.builder()
                .classNumber(103)
                .year(2019)
                .semester(1)
                .subject("전공필수")
                .major(major)
                .grade(grade)
                .title(title)
                .credit("3학점")
                .location("A13-2126")
                .professor("홍길동")
                .time("월:[6](14:00-14:50) 수:[2][3](10:00-11:50)")
                .build();
        return courseRepository.save(course);
    }

    private Student getStudentAndJoin(String studentNumber, String password) {
        StudentJoinRequestDto studentDto = StudentJoinRequestDto.builder()
                .studentNumber(studentNumber)
                .password(password)
                .email("test@email.com")
                .major("컴퓨터 공학과")
                .name("홍길동")
                .roles(Collections.singleton(StudentRole.USER))
                .build();
        return studentService.join(studentDto);
    }

}