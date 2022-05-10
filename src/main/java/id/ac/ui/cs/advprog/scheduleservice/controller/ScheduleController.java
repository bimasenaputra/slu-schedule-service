package id.ac.ui.cs.advprog.scheduleservice.controller;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity createSchedule(@RequestParam(name="uid") String uid, @RequestBody Map<String, Object> schedule) {
        var title = schedule.get("title").toString();
        var startTime = schedule.get("startTime").toString();
        var endTime = schedule.get("endTime").toString();
        var startingLoc = schedule.get("startingLoc").toString();
        var destination = schedule.get("destination").toString();
        var desc = schedule.get("desc").toString();

        var newSchedule = new Schedule();
        newSchedule.setTitle(title);
        newSchedule.setUser(uid);
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

    @GetMapping(path = "/{id}/{user}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Schedule> getSchedule(@PathVariable(value = "id") int id, @PathVariable(value = "user") String user) {
        Schedule schedule = scheduleService.getScheduleById(id, user) ;
        if (schedule == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(schedule);
    }

    @GetMapping(path = "/{user}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<List<Schedule>> getAllUserSchedule(@PathVariable(value = "user") String user) {
        List<Schedule> userSchedules = scheduleService.getAllUserSchedule(user) ;
        return ResponseEntity.ok(userSchedules);
    }
}
