package id.ac.ui.cs.advprog.scheduleservice.repository;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Schedule findById(int id, String user) ;
}
