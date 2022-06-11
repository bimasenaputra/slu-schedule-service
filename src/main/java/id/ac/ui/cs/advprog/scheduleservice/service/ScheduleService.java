package id.ac.ui.cs.advprog.scheduleservice.service;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;

import java.util.Optional;

public interface ScheduleService {
    Schedule createSchedule(Schedule schedule);
    Iterable<Schedule> getSchedules();
    Optional<Schedule> getSchedule(Long id);
    void deleteSchedule(Schedule schedule);
    Schedule updateSchedule(Long id, Schedule schedule);
    Iterable<Schedule> getUserSchedules(String user);
    boolean checkUserScheduleTime(String startTime, String uid);

    boolean checkUpdateUserScheduleTime(String startTime, String uid, Long sid);
}
