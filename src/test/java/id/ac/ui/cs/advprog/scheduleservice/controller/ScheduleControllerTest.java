package id.ac.ui.cs.advprog.scheduleservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.scheduleservice.model.Schedule;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ScheduleService scheduleService;

    private final Schedule schedule1 = new Schedule();
    private final Schedule schedule2 = new Schedule();

    @BeforeEach
    public void setup() {
        schedule1.setId(137L);
        schedule1.setTitle("Test1");
        schedule1.setUser("abc");
        schedule1.setStartTime("2022-05-10T17:03");
        schedule1.setEndTime("2022-05-11T17:03");
        schedule1.setStartingLoc("Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        schedule1.setDestination("Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        schedule1.setDesc("test sprint 2");

        schedule2.setId(248L);
        schedule2.setTitle("Test2");
        schedule2.setUser("xyz");
        schedule2.setStartTime("2022-05-10T17:03");
        schedule2.setEndTime("2022-05-11T17:03");
        schedule2.setStartingLoc("Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        schedule2.setDestination("Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        schedule2.setDesc("test sprint 2");
    }

    @Test
    public void getAllSchedule_success() throws Exception {
        Iterable<Schedule> records = new ArrayList<>(Arrays.asList(schedule1, schedule2));

        Mockito.when(scheduleService.getSchedules()).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].title", is("Test2")));
    }

    @Test
    public void getUserSchedules_success() throws Exception {
        Iterable<Schedule> records = new ArrayList<>(Arrays.asList(schedule1));

        Mockito.when(scheduleService.getUserSchedules(schedule1.getUser())).thenReturn(records);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/schedules?uid="+schedule1.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("Test1")));
    }

    @Test
    public void getScheduleById_success() throws Exception {
        Mockito.when(scheduleService.getSchedule(schedule1.getId())).thenReturn(java.util.Optional.of(schedule1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/137?uid="+schedule1.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test1")));
    }

    @Test
    public void getScheduleById_fail() throws Exception {
        Mockito.when(scheduleService.getSchedule(schedule1.getId())).thenReturn(java.util.Optional.of(schedule1));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/137?uid="+schedule2.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkScheduleTime_success() throws Exception {
        Mockito.when(scheduleService.checkUserScheduleTime(schedule2.getStartTime(), schedule2.getUser())).thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/filter/"+schedule2.getStartTime()+"?uid="+schedule2.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$", is(false)));
    }

    @Test
    public void createSchedule_success() throws Exception {
        var newRecord = new Schedule();
        newRecord.setTitle(schedule1.getTitle());
        newRecord.setUser(schedule1.getUser());
        newRecord.setStartTime(schedule1.getStartTime());
        newRecord.setEndTime(schedule1.getEndTime());
        newRecord.setStartingLoc(schedule1.getStartingLoc());
        newRecord.setDestination(schedule1.getDestination());
        newRecord.setDesc(schedule1.getDesc());

        Mockito.when(scheduleService.createSchedule(newRecord)).thenReturn(newRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/?uid="+schedule1.getUser())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(newRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test1")));
    }

    @Test
    public void updatePatientRecord_success() throws Exception {
        var updatedRecord = new Schedule();
        updatedRecord.setTitle("Test3");
        updatedRecord.setUser(schedule1.getUser());
        updatedRecord.setStartTime(schedule1.getStartTime());
        updatedRecord.setEndTime(schedule1.getEndTime());
        updatedRecord.setStartingLoc(schedule1.getStartingLoc());
        updatedRecord.setDestination(schedule1.getDestination());
        updatedRecord.setDesc(schedule1.getDesc());

        Mockito.when(scheduleService.getSchedule(schedule1.getId())).thenReturn(Optional.of(schedule1));
        Mockito.when(scheduleService.updateSchedule(schedule1.getId(), updatedRecord)).thenReturn(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/137?uid="+schedule1.getUser())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.title", is("Test3")));
    }

    @Test
    public void updatePatientRecord_fail() throws Exception {
        var updatedRecord = new Schedule();
        updatedRecord.setTitle("Test3");
        updatedRecord.setUser(schedule1.getUser());
        updatedRecord.setStartTime(schedule1.getStartTime());
        updatedRecord.setEndTime(schedule1.getEndTime());
        updatedRecord.setStartingLoc(schedule1.getStartingLoc());
        updatedRecord.setDestination(schedule1.getDestination());
        updatedRecord.setDesc(schedule1.getDesc());

        Mockito.when(scheduleService.getSchedule(schedule1.getId())).thenReturn(Optional.of(schedule1));
        Mockito.when(scheduleService.updateSchedule(schedule1.getId(), updatedRecord)).thenReturn(updatedRecord);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.put("/137?uid="+schedule2.getUser())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(updatedRecord));

        mockMvc.perform(mockRequest).andExpect(status().isNotFound());
    }

    @Test
    public void deleteSchedule_success() throws Exception {
        Mockito.when(scheduleService.getSchedule(schedule2.getId())).thenReturn(Optional.of(schedule2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/248?uid="+schedule2.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteSchedule_fail() throws Exception {
        Mockito.when(scheduleService.getSchedule(schedule2.getId())).thenReturn(Optional.of(schedule2));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/248?uid="+schedule1.getUser())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
