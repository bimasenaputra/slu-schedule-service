package id.ac.ui.cs.advprog.scheduleservice.repository;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findById(Long id) ;
}
