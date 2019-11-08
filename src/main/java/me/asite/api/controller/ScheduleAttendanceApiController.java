package me.asite.api.controller;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.AttendanceAddRequestDto;
import me.asite.api.response.IsSuccessReponse;
import me.asite.domain.ScheduleAttendance;
import me.asite.exception.AttendanceFailException;
import me.asite.service.AttendanceService;
import me.asite.service.ScheduleAttendanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ScheduleAttendanceApiController {

    private final ScheduleAttendanceService scheduleAttendanceService;
    private final AttendanceService attendanceService;

    @PostMapping("/schedule/add")
    public void addSchedule(@RequestParam("studentId") Long studentId,
                            @RequestParam("courseId") Long courseId) {
        scheduleAttendanceService.addScheduleAttendance(studentId, courseId);
    }

    @DeleteMapping("/schedule/delete/{scheduleId}")
    public void deleteSchedule(@PathVariable("scheduleId") Long scheduleId) {
        scheduleAttendanceService.deleteScheduleAttendance(scheduleId);
    }

    @PutMapping("/schedule/attendancecheck/{scheduleId}")
    public IsSuccessReponse attendanceCheck(@PathVariable("scheduleId") Long scheduleId,
                                            @RequestBody AttendanceAddRequestDto dto) {
        try {

            ScheduleAttendance scheduleAttendance
                    = scheduleAttendanceService.countAttendance(scheduleId, dto.getAttendanceState());
            attendanceService.attendanceCheck(scheduleAttendance, dto);
            return new IsSuccessReponse(true);

        } catch (AttendanceFailException e) {

            return new IsSuccessReponse(false);

        }
    }


}
