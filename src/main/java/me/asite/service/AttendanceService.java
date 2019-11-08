package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.AttendanceAddRequestDto;
import me.asite.domain.Attendance;
import me.asite.domain.ScheduleAttendance;
import me.asite.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;


    public void attendanceCheck(ScheduleAttendance scheduleAttendance, AttendanceAddRequestDto dto) {
        Attendance attendnace = Attendance.createAttendnace
                (scheduleAttendance, dto.getAttendanceDate(), dto.getStartTime()
                        , dto.getEndTime(), dto.getAttendanceState(), dto.getFinishState());
        attendanceRepository.save(attendnace);
    }
}
