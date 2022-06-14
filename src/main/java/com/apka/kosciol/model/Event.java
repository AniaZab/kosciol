package com.apka.kosciol.model;

import lombok.Data;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class Event implements Serializable {
    String category;
    @NotEmpty(message = "You have to enter a title!")
    String title;
    @NotEmpty(message = "You have to enter a start date!")
    String start_date;
    @NotEmpty(message = "You have to enter a start time!")
    String start_time;
    @NotEmpty(message = "You have to enter a finish date!")
    String finish_date;
    @NotEmpty(message = "You have to enter a finish time!")
    String finish_time;
    @NotEmpty(message = "You have to enter for who is the event!")
    String for_who;
    @NotEmpty(message = "You have to enter a description!")
    String description;
    String extraInfo;
}
