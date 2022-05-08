package id.ac.ui.cs.advprog.scheduleservice.controller;

import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

@WebMvcTest(controllers = ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ScheduleService scheduleService;

    private Map<String, Object> schedMap = new HashMap<>();
    private Schedule schedule = new Schedule();

    @BeforeEach
    public void setup() {
        schedMap.put("title", "Test");
        schedMap.put("startTime", "2022-05-10T17:03");
        schedMap.put("endTime", "2022-05-11T17:03");
        schedMap.put("startingLoc", "Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        schedMap.put("destination", "Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        schedMap.put("desc", "test sprint 2");

        schedule.setTitle("Test");
        schedule.setStartTime("2022-05-10T17:03");
        schedule.setEndTime("2022-05-11T17:03");
        schedule.setStartingLoc("Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        schedule.setDestination("Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        schedule.setDesc("test sprint 2");
    }
}
