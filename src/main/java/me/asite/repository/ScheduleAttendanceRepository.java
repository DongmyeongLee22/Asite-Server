package me.asite.repository;

import me.asite.domain.ScheduleAttendace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleAttendanceRepository extends JpaRepository<ScheduleAttendace, Long> {
}
