package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.AttendanceCheckRequestDto;
import me.asite.exception.CannotFindByIDException;
import me.asite.student.CurrentStudent;
import me.asite.student.Student;
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

    @PostMapping("/add")
    public ResponseEntity addTimetable(@RequestBody @Valid TimetableAddRequestDto dto,
                                       @CurrentStudent Student student,
                                       Errors errors) {

        if (errors.hasErrors() || !student.getId().equals(dto.getStudentId())) {
            return ResponseEntity.badRequest().build();
        }

        Timetable timetable = timetableService.addTimetable(dto.getStudentId(), dto.getCourseId());
        TimetableResource timetableResource = new TimetableResource(timetable);
        timetableResource.add(new Link("/docs/index.html#resources-timetable-add").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @GetMapping
    public ResponseEntity query_TimetableByStudentId(@RequestParam Long studentId,
                                                     @CurrentStudent Student student) {
        try {

            if (!student.getId().equals(studentId)) {
                return ResponseEntity.badRequest().build();
            }

            List<Timetable> findTimetables = timetableService.findAllWithCourseByStudentId(studentId);

            Resources<TimetableResource> timetableResources = getTimetableResources(findTimetables);

            timetableResources.add(linkTo(TimetableController.class).slash("queryByStudentId").slash(studentId).withSelfRel());
            timetableResources.add(new Link("/docs/index.html#resources-query-timetable").withRel("profile"));
            return ResponseEntity.ok(timetableResources);

        } catch (CannotFindByIDException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity attendanceCheck(@PathVariable("id") Long timetableId,
                                          @RequestParam Long studentId,
                                          @CurrentStudent Student student,
                                          @RequestBody @Valid AttendanceCheckRequestDto dto,
                                          Errors errors) {

        if (errors.hasErrors() || !student.getId().equals(studentId)) {
            return ResponseEntity.badRequest().build();
        }

        Timetable checkedTimetable = timetableService.attendanceCheck(timetableId, dto);


        TimetableResource timetableResource = new TimetableResource(checkedTimetable);
        timetableResource.add(new Link("/docs/index.html#resources-attendance-check").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTimetable(@PathVariable("id") Long timetableId,
                                          @RequestParam Long studentId,
                                          @CurrentStudent Student student) {

        if (!student.getId().equals(studentId)) {
            return ResponseEntity.badRequest().build();
        }

        timetableService.deleteTimetable(timetableId);

        return ResponseEntity.noContent().build();
    }


    private Resources<TimetableResource> getTimetableResources(List<Timetable> findTimetables) {
        List<TimetableResource> collect = findTimetables.stream()
                .map(TimetableResource::new)
                .collect(toList());
        return new Resources<>(collect);
    }
}
