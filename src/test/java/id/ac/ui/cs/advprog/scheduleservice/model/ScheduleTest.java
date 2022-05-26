package id.ac.ui.cs.advprog.scheduleservice.model;

import id.ac.ui.cs.advprog.scheduleservice.repository.ScheduleRepository;
import id.ac.ui.cs.advprog.scheduleservice.service.ScheduleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ScheduleTest {
    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    private final String ISO_8601_REGEX = "(\\d{4}-\\d{2}-\\d{2})[A-Z]+(\\d{2}:\\d{2})";
    private final Schedule newschedule = new Schedule();

    @BeforeEach
    public void setup() {
        newschedule.setTitle("Test dua");
        newschedule.setUser("testestest");
        newschedule.setStartTime("2022-05-10T17:03");
        newschedule.setEndTime("2022-05-11T17:03");
        newschedule.setStartingLoc("Alam Sutera, Jalan Raya Serpong, Pondok Jagung, South Tangerang City, Banten, Indonesia");
        newschedule.setDestination("Bandara Soekarno Hatta, Tangerang City, Banten, Indonesia");
        newschedule.setDesc("");

        scheduleService.createSchedule(newschedule);
    }

    @Test
    public void start_time_format_iso_8601() {
        assertTrue(newschedule.getStartTime().matches(ISO_8601_REGEX));
    }

    @Test
    public void end_time_format_iso_8601() {
        assertTrue(newschedule.getEndTime().matches(ISO_8601_REGEX));
    }

    @Test
    public void start_loc_not_empty() {
        assertFalse(newschedule.getStartingLoc().isEmpty());
    }

    @Test
    public void destination_not_empty() {
        assertFalse(newschedule.getDestination().isEmpty());
    }

    @Test
    public void desc_can_be_empty() {
        assertTrue(newschedule.getDesc().isEmpty());
    }
}
