package id.ac.ui.cs.advprog.scheduleservice.repository;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}
