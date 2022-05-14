package id.ac.ui.cs.advprog.scheduleservice.service;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;

import java.util.Optional;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule);
    Iterable<Schedule> getSchedules();
    Iterable<Schedule> getUserSchedules(String user);
    Optional<Schedule> getSchedule(Long id);
    void deleteSchedule(Schedule schedule);
    Schedule updateSchedule(Long id, Schedule schedule);
    boolean checkUserScheduleTime(String startTime, String uid);
}
