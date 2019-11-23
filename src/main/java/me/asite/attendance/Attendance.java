package me.asite.attendance;

import lombok.*;
import me.asite.timetable.Timetable;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attendance {

    @Id
    @GeneratedValue
    @Column(name = "attendance_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timetable_id")
    private Timetable timetable;

    private LocalDate attendanceDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Enumerated(EnumType.STRING)
    private AttendanceState attendanceState;

    @Enumerated(EnumType.STRING)
    private AttendanceEndState attendanceEndState;

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}
