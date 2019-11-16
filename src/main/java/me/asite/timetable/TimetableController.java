package me.asite.timetable;

import lombok.RequiredArgsConstructor;
import me.asite.attendance.AttendanceCheckRequestDto;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @PutMapping("/{id}")
    public ResponseEntity attendanceCheck(@PathVariable("id") Long timetableId,
                                          @RequestBody @Valid AttendanceCheckRequestDto dto,
                                          Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        Timetable checkedTimetable = timetableService.attendanceCheck(timetableId, dto);
        TimetableResource timetableResource = new TimetableResource(checkedTimetable);
        timetableResource.add(new Link("/docs/index.html#resources-attendancheck").withRel("profile"));

        return ResponseEntity.ok(timetableResource);
    }

    @GetMapping("/timetable/list")
    public List<TimetableListRequestDto> showTimetables(@ModelAttribute("studentId") Long studentId) {
        List<Timetable> timetables = timetableService.findAllWithCourseByStudentId(studentId);
        return timetables.stream()
                .map(TimetableListRequestDto::new)
                .collect(toList());
    }


}
