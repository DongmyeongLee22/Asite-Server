package me.asite.api.controller;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.AttendanceAddRequestDto;
import me.asite.api.response.IsSuccessReponse;
import me.asite.domain.Timetable;
import me.asite.exception.AttendanceFailException;
import me.asite.service.AttendanceService;
import me.asite.service.TimetableService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class TimetableApiController {

    private final TimetableService timetableService;
    private final AttendanceService attendanceService;

    @PostMapping("/timetable/add")
    public void addTimetable(@RequestParam("studentId") Long studentId,
                             @RequestParam("courseId") Long courseId) {
        timetableService.addTimetable(studentId, courseId);
    }

    @DeleteMapping("/timetable/delete/{timetableId}")
    public void deleteTimetable(@PathVariable("timetableId") Long timetableId) {
        timetableService.deleteTimetable(timetableId);
    }

    @PutMapping("/timetable/attendancecheck/{timetableId}")
    public IsSuccessReponse attendanceCheck(@PathVariable("timetableId") Long timetableId,
                                            @RequestBody AttendanceAddRequestDto dto) {
        try {

            Timetable timetable = timetableService.countAttendance(timetableId, dto.getAttendanceState());
            attendanceService.attendanceCheck(timetable, dto);
            return new IsSuccessReponse(true);

        } catch (AttendanceFailException e) {

            return new IsSuccessReponse(false);

        }
    }

    @GetMapping("/timetable/list")
    public List<TimetableListRequestDto> showTimetables(@ModelAttribute("studentId") Long studentId) {
        List<Timetable> timetables = timetableService.findAllWithCourseByStudentId(studentId);
        return timetables.stream()
                .map(TimetableListRequestDto::new)
                .collect(toList());
    }


}
