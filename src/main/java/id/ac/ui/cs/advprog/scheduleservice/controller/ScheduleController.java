package id.ac.ui.cs.advprog.scheduleservice.controller;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.CompletableFuture;


@RestController
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping(path = {"/all"}, produces = {"application/json"})
    public ResponseEntity<Iterable<Schedule>> getAllSchedule() {
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

    @GetMapping("/schedules")
    public ResponseEntity<Iterable<Schedule>> getUserSchedules(@RequestParam(name="uid") String uid) {
        return ResponseEntity.ok(scheduleService.getUserSchedules(uid));
    }

    @GetMapping(path = "/filter/{startTime}", produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<Boolean> checkScheduleTime(@RequestParam(name="uid") String uid, @PathVariable(value = "startTime") String startTime) {
        boolean result = scheduleService.checkUserScheduleTime(startTime, uid);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public ResponseEntity<Schedule> createSchedule(@RequestParam(name = "uid") String uid, @RequestBody Schedule schedule) {
        var newSchedule = createNewSchedule(uid, schedule);
        return ResponseEntity.ok(scheduleService.createSchedule(newSchedule));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable String id, @RequestParam(name = "uid") String uid, @RequestBody Schedule schedule) {
        var getSchedule = scheduleService.getSchedule(Long.parseLong(id));
        if (getSchedule.isEmpty() || !getSchedule.get().getUser().equals(uid)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var updatedSchedule = createNewSchedule(uid, schedule);

        return ResponseEntity.ok(scheduleService.updateSchedule(Long.parseLong(id), updatedSchedule));

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

    private Schedule createNewSchedule(@RequestParam(name = "uid") String uid, @RequestBody Schedule schedule) {
        var title = schedule.getTitle();
        var startTime = schedule.getStartTime();
        var endTime = schedule.getEndTime();
        var startingLoc = schedule.getStartingLoc();
        var destination = schedule.getDestination();
        var desc = schedule.getDesc();

        var newSchedule = new Schedule();
        newSchedule.setTitle(title);
        newSchedule.setUser(uid);
        newSchedule.setStartTime(startTime);
        newSchedule.setEndTime(endTime);
        newSchedule.setStartingLoc(startingLoc);
        newSchedule.setDestination(destination);
        newSchedule.setDesc(desc);
        return newSchedule;
    }

}
