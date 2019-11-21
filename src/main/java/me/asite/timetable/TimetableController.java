package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.AttendanceCheckRequestDto;
import me.asite.common.ErrorsResource;
import me.asite.student.CurrentStudent;
import me.asite.student.Student;
import me.asite.timetable.dto.TimetableAddRequestDto;
import me.asite.timetable.dto.TimetableDeleteRequestDto;
import me.asite.timetable.dto.TimetableQueryRequestDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/api/timetable", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class TimetableController {

    private final TimetableService timetableService;
    private final StudentValidator studentValidator;

    @PostMapping("/add")
    public ResponseEntity addTimetable(@RequestBody @Valid TimetableAddRequestDto dto,
                                       @CurrentStudent Student student,
                                       Errors errors) {

        if (validate(student, errors, dto.getStudentId())) return badRequest(errors);


        Timetable timetable = timetableService.addTimetable(dto.getStudentId(), dto.getCourseId());
        TimetableResource timetableResource = new TimetableResource(timetable);
        timetableResource.add(new Link("/docs/index.html#resources-timetable-add").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @GetMapping
    public ResponseEntity query_TimetableByStudentId(@RequestBody @Valid TimetableQueryRequestDto dto,
                                                     @CurrentStudent Student student,
                                                     Errors errors) {

        if (validate(student, errors, dto.getStudentId())) return badRequest(errors);

        List<Timetable> findTimetables = timetableService.findAllWithCourseByStudentId(dto.getStudentId());

        Resources<TimetableResource> timetableResources = getTimetableResources(findTimetables);

        timetableResources.add(linkTo(TimetableController.class).slash("queryByStudentId").slash(dto.getStudentId()).withSelfRel());
        timetableResources.add(new Link("/docs/index.html#resources-query-timetable").withRel("profile"));
        return ResponseEntity.ok(timetableResources);

    }

    @PutMapping("/{id}")
    public ResponseEntity attendanceCheck(@PathVariable("id") Long timetableId,
                                          @CurrentStudent Student student,
                                          @RequestBody @Valid AttendanceCheckRequestDto dto,
                                          Errors errors) {

        if (validate(student, errors, dto.getStudentId())) return badRequest(errors);

        Timetable checkedTimetable = timetableService.attendanceCheck(timetableId, dto);


        TimetableResource timetableResource = new TimetableResource(checkedTimetable);
        timetableResource.add(new Link("/docs/index.html#resources-attendance-check").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTimetable(@PathVariable("id") Long timetableId,
                                          @RequestBody @Valid TimetableDeleteRequestDto dto,
                                          @CurrentStudent Student student,
                                          Errors errors) {

        if (validate(student, errors, dto.getStudentId())) return badRequest(errors);

        timetableService.deleteTimetable(timetableId);

        return ResponseEntity.noContent().build();
    }

    /**
     * 처음은 @Valid로 생긴 에러를 확인하고 두번째는 validater를 통해 검증한 에러를 확인한다.
     */
    private boolean validate(@CurrentStudent Student student, Errors errors, Long studentId) {
        if (errors.hasErrors()) {
            return true;
        }

        studentValidator.validateStudentId(studentId, student.getId(), errors);

        return errors.hasErrors();
    }


    /**
     * TimetableList들을 TimetableResource로 매핑한다.
     */
    private Resources<TimetableResource> getTimetableResources(List<Timetable> findTimetables) {
        List<TimetableResource> collect = findTimetables.stream()
                .map(TimetableResource::new)
                .collect(toList());
        return new Resources<>(collect);
    }

    /**
     * body에 ErrorsResouce를 넣고 BadRequest로 반환한다.
     */
    private ResponseEntity<ErrorsResource> badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(new ErrorsResource(errors));
    }
}
