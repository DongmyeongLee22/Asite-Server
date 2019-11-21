package me.asite.timetable.dto;

import lombok.Data;
import me.asite.timetable.Timetable;

@Data
public class TimetableListRequestDto {

    private Long id;
    private int attendanceCount;
    private int latelessCount;
    private int absentCount;

    private String time;
    private String title;
    private int class_Number;

    public TimetableListRequestDto(Timetable timetable){
        id = timetable.getId();
        attendanceCount = timetable.getAttendanceCount();
        latelessCount = timetable.getLatelessCount();
        absentCount = timetable.getAbsentCount();
        time = timetable.getCourse().getTime();
        title = timetable.getCourse().getTitle();
        class_Number = timetable.getCourse().getClassNumber();
    }
}
