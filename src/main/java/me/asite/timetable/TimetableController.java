package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.AttendanceCheckRequestDto;
import me.asite.exception.CannotFindByIDException;
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
                                       Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Timetable timetable = timetableService.addTimetable(dto.getStudentId(), dto.getCourseId());
        TimetableResource timetableResource = new TimetableResource(timetable);
        timetableResource.add(new Link("/docs/index.html#resources-timetable-add").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @GetMapping("/queryByStudentId/{id}")
    public ResponseEntity query_TimetableByStudentId(@PathVariable("id") Long studentId) {

        try {

            List<Timetable> findTimetables = timetableService.findAllWithCourseByStudentId(studentId);
            List<TimetableResource> collect = findTimetables.stream()
                    .map(TimetableResource::new)
                    .collect(toList());
            Resources<TimetableResource> timetableResources = new Resources<>(collect);
            timetableResources.add(linkTo(TimetableController.class).slash("queryByStudentId").slash(studentId).withSelfRel());
            timetableResources.add(new Link("/docs/index.html#resources-query-timetable").withRel("profile"));
            return ResponseEntity.ok(timetableResources);

        } catch (CannotFindByIDException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity attendanceCheck(@PathVariable("id") Long timetableId,
                                          @RequestBody @Valid AttendanceCheckRequestDto dto,
                                          Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Timetable checkedTimetable = timetableService.attendanceCheck(timetableId, dto);
        TimetableResource timetableResource = new TimetableResource(checkedTimetable);
        timetableResource.add(new Link("/docs/index.html#resources-attendance-check").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTimetable(@PathVariable("id") Long timetableId) {
        timetableService.deleteTimetable(timetableId);
        //TODO 현재 유저가 시간표의 주인인지 확인하는 검증 필요
        Link link = new Link("/docs/index.html#resources-attendance-check");
        return ResponseEntity.ok().build();
    }

}
