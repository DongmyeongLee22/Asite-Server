package me.asite.service;

import lombok.RequiredArgsConstructor;
import me.asite.api.dto.AttendanceAddRequestDto;
import me.asite.domain.Attendance;
import me.asite.domain.Timetable;
import me.asite.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;


    public void attendanceCheck(Timetable timetable, AttendanceAddRequestDto dto) {
        Attendance attendnace = Attendance.createAttendnace
                (timetable, dto.getAttendanceDate(), dto.getStartTime()
                        , dto.getEndTime(), dto.getAttendanceState(), dto.getAttendanceEndState());
        attendanceRepository.save(attendnace);
    }
}
