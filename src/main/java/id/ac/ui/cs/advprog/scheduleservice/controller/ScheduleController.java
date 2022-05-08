package id.ac.ui.cs.advprog.scheduleservice.controller;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestParam(name="uid") String uid, @RequestBody String s) {
        System.out.println("uid: " + uid);
        System.out.println("msg: " + s);
        return ResponseEntity.ok("berhasil");
    }

    @PostMapping("/createSchedule")
    public ResponseEntity createSchedule(@RequestBody Map<String, Object> schedule) {
        var user = schedule.get("user").toString();
        var title = schedule.get("title").toString();
        var startTime = schedule.get("startTime").toString();
        var endTime = schedule.get("endTime").toString();
        var startingLoc = schedule.get("startingLoc").toString();
        var destination = schedule.get("destination").toString();
        var desc = schedule.get("desc").toString();

        var newSchedule = new Schedule();
        newSchedule.setId(scheduleService.count()+1);
        newSchedule.setTitle(title);
        newSchedule.setUser(user);
        newSchedule.setStartTime(startTime);
        newSchedule.setEndTime(endTime);
        newSchedule.setStartingLoc(startingLoc);
        newSchedule.setDestination(destination);
        newSchedule.setDesc(desc);

        return ResponseEntity.ok(scheduleService.createSchedule(newSchedule));
    }

    @GetMapping(path = {"/getAll"}, produces = {"application/json"})
    public ResponseEntity<Iterable<Schedule>> getAllSchedule(){
        return ResponseEntity.ok(scheduleService.getSchedules());
    }
}
