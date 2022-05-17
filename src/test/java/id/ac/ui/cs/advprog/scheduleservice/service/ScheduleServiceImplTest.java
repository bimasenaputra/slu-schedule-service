package id.ac.ui.cs.advprog.scheduleservice.service;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import java.util.ArrayList;
import java.util.List;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ScheduleServiceImplTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private final Schedule newschedule = new Schedule();

    @BeforeEach
    public void setup() {
        newschedule.setTitle("Test dua");
        newschedule.setUser("testestest");
        newschedule.setStartTime("2022-05-10T17:03");
        newschedule.setEndTime("2022-05-11T17:03");
        newschedule.setStartingLoc("Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        newschedule.setDestination("Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        newschedule.setDesc("test sprint 22222");

        scheduleService.createSchedule(newschedule);
    }

    @Test
    public void createScheduleTest() {
        Schedule schedule = new Schedule();
        schedule.setTitle("Test");
        schedule.setUser("testestest");
        schedule.setStartTime("2022-05-10T17:03");
        schedule.setEndTime("2022-05-11T17:03");
        schedule.setStartingLoc("Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        schedule.setDestination("Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        schedule.setDesc("test sprint 2");

        scheduleService.createSchedule(schedule);
        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    public void getSchedulesTest() throws Exception {
        Iterable<Schedule> scheduleList = scheduleRepository.findAll();
        lenient().when(scheduleService.getSchedules()).thenReturn(scheduleList);
        Iterable<Schedule> scheduleListResult = scheduleService.getSchedules();
        assertIterableEquals(scheduleList, scheduleListResult);
    }

    @Test
    public void getScheduleByIdTest() {
        lenient().when(scheduleService.getSchedule(newschedule.getId())).thenReturn(Optional.of(newschedule));
        Optional<Schedule> schedule = scheduleService.getSchedule(newschedule.getId());
        assertEquals(schedule.get().getId(), newschedule.getId());
    }

    @Test
    public void getUserSchedulesTest() {
        List<Schedule> schedulesMock = new ArrayList<>();
        Schedule scheduleMock = new Schedule();
        String uid = anyString();
        scheduleMock.setUser(uid);
        schedulesMock.add(scheduleMock);
        lenient().when(scheduleService.getUserSchedules(uid)).thenReturn(schedulesMock);
        Iterable<Schedule> schedulesReturn = scheduleService.getUserSchedules(uid);
        assertIterableEquals(schedulesMock, schedulesReturn);
    }

    @Test
    public void deleteScheduleTest(){
        Schedule scheduleMock = new Schedule(9999L,"id","title","startTime","endTime","startingLoc","destination","desc");
        scheduleService.deleteSchedule(scheduleMock);
        verify(scheduleRepository).delete(scheduleMock);
    }

    @Test
    public void updateScheduleTest() {
        String pastTitle = newschedule.getTitle();
        String newTitle = "Test Update";
        newschedule.setTitle(newTitle);

        lenient().when(scheduleService.updateSchedule(newschedule.getId(), newschedule)).thenReturn(newschedule);
        Schedule scheduleResult = scheduleService.updateSchedule(newschedule.getId(), newschedule);

        assertEquals(scheduleResult.getId(), newschedule.getId());
        assertNotEquals(scheduleResult.getTitle(), pastTitle);
    }

    @Test
    public void checkUserScheduleTimeTest() {
        String startTime = "2022-05-10T17:02";
        String uid = "testestest";
        boolean result = scheduleService.checkUserScheduleTime(startTime, uid);
        assertEquals(result, true);

    }
}
