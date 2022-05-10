package id.ac.ui.cs.advprog.scheduleservice.service;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService{

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public Schedule createSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    @Override
    public Iterable<Schedule> getSchedules() {
        return scheduleRepository.findAll();
    }

    @Override
    public Schedule getScheduleById(int id, String user) {
        return scheduleRepository.findById(id, user);
    }

    @Override
    public List<Schedule> getAllUserSchedule(String user) {
        List<Schedule> temp = new ArrayList<>();
        for (Schedule i : scheduleRepository.findAll()) {
            if (i.getUser().equals(user)) {
                temp.add(i) ;
            }
        }
        return temp;
    }
}
