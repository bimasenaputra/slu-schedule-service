package id.ac.ui.cs.advprog.scheduleservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "Schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private String user;

    @Column(name = "title")
    private String title;

    @Column(name = "start_time")
    private String startTime;

    @Column(name = "end_time")
    private String endTime;

    @Column(name = "starting_loc")
    private String startingLoc;

    @Column(name = "destination")
    private String destination;

    @Column(name = "description")
    private String desc;
}

