package id.ac.ui.cs.advprog.scheduleservice.controller;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

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

    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getSchedule(@PathVariable String id, @RequestParam(name="uid") String uid) {
        var schedule = scheduleService.getSchedule(Long.parseLong(id));
        if (schedule.isEmpty() || !schedule.get().getUser().equals(uid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(schedule.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id, @RequestParam(name = "uid") String uid) {
        var schedule = scheduleService.getSchedule(Long.parseLong(id));
        if (schedule.isEmpty() || !schedule.get().getUser().equals(uid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        CompletableFuture.runAsync(() -> scheduleService.deleteSchedule(schedule.get()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
