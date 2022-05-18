package id.ac.ui.cs.advprog.scheduleservice.service;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule createSchedule(Schedule schedule) throws IllegalArgumentException {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Iterable<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }


    @Override
    public Iterable<Schedule> getUserSchedules(String user) {
        return scheduleRepository.findAllByUser(user);
    }

    @Override
    public Optional<Schedule> getSchedule(Long id) throws IllegalArgumentException { return scheduleRepository.findById(id); }

    @Override
    public void deleteSchedule(Schedule schedule) throws IllegalArgumentException { scheduleRepository.delete(schedule); }

    @Override
    public Schedule updateSchedule(Long id, Schedule schedule) {
        schedule.setId(id);
        return scheduleRepository.save(schedule);
    }

    @Override
    public boolean checkUserScheduleTime(String startTime, String uid) {
        var userSchedule = getUserSchedules(uid);
        return StreamSupport.stream(userSchedule.spliterator(), true).noneMatch(schedule -> schedule.getStartTime().compareTo(startTime) == 0);
    }

}
