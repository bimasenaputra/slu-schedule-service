package id.ac.ui.cs.advprog.scheduleservice.controller;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@RestController
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;


    @PostMapping("/test")
    public ResponseEntity<String> test(@RequestParam(name = "uid") String uid, @RequestBody String s) {
        System.out.println("uid: " + uid);
        System.out.println("msg: " + s);
        return ResponseEntity.ok("berhasil");
    }

    @PostMapping("/createSchedule")
    public ResponseEntity createSchedule(@RequestParam(name = "uid") String uid, @RequestBody Map<String, Object> schedule) {
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
    public ResponseEntity<Iterable<Schedule>> getAllSchedule() {
        return ResponseEntity.ok(scheduleService.getSchedules());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable String id, @RequestParam(name = "uid") String uid) {
        CompletableFuture.supplyAsync(() -> scheduleService.getSchedule(Long.parseLong(id)))
                .thenAccept(scheduleOpt -> {
                    var schedule = scheduleOpt.orElse(null);
                    assert schedule != null;
                    if (schedule.getUser().equals(uid)) scheduleService.deleteSchedule(schedule);
                    else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                })
                .exceptionallyAsync(ex -> {
                    System.out.println("Error delete: " + ex.getMessage());
                    return null;
                });
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable String id, @RequestParam(name = "uid") String uid, @RequestBody Map<String, Object> schedule) {
        var getSchedule = scheduleService.getSchedule(Long.parseLong(id));
        if (getSchedule.isEmpty() || !getSchedule.get().getUser().equals(uid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var title = schedule.get("title").toString();
        var startTime = schedule.get("startTime").toString();
        var endTime = schedule.get("endTime").toString();
        var startingLoc = schedule.get("startingLoc").toString();
        var destination = schedule.get("destination").toString();
        var desc = schedule.get("desc").toString();

        var updatedSchedule = new Schedule();
        updatedSchedule.setTitle(title);
        updatedSchedule.setUser(uid);
        updatedSchedule.setStartTime(startTime);
        updatedSchedule.setEndTime(endTime);
        updatedSchedule.setStartingLoc(startingLoc);
        updatedSchedule.setDestination(destination);
        updatedSchedule.setDesc(desc);

        return ResponseEntity.ok(scheduleService.updateSchedule(Long.parseLong(id), updatedSchedule));

    }

    @GetMapping(path = "/{id}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Optional<Schedule>> getSchedule(@PathVariable(value = "id") Long id) {
        Optional<Schedule> schedule = scheduleService.getScheduleById(id);
        if (schedule == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(schedule);
    }


    @GetMapping(path = "/getAllSchedule", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<List<Schedule>> getAllScheduleUser(@RequestParam(name = "uid") String uid) {
        List<Schedule> userSchedules = scheduleService.getAllScheduleUser(uid);
        return ResponseEntity.ok(userSchedules);
    }

    @GetMapping("/schedules")
    public ResponseEntity<Iterable<Schedule>> getAllSchedule(@RequestParam(name="uid") String uid) {
        return ResponseEntity.ok(scheduleService.getUserSchedules(uid));
    }

    @GetMapping(path = "/checkSchedTime/{startTime}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Boolean> checkSchedTime(@RequestParam(name="uid") String uid, @PathVariable(value = "startTime") String startTime) {
        boolean result = scheduleService.checkUserScheduleTime(startTime, uid);
        return ResponseEntity.ok(result);
    }

}
