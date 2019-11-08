package me.asite.repository;

import me.asite.domain.ScheduleAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleAttendanceRepository extends JpaRepository<ScheduleAttendance, Long> {
}
