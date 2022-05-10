package id.ac.ui.cs.advprog.scheduleservice.service;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule);
    Iterable<Schedule> getSchedules();
    Schedule getScheduleById(int id, String user);
    List<Schedule> getAllUserSchedule(String user);
}
