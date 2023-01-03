package com.apka.kosciol.dto;

import com.apka.kosciol.entity.MeetingCategory;
import com.apka.kosciol.entity.RecipientCategory;
import com.apka.kosciol.entity.Status;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.time.LocalTime;

@Data
public class EventDto {

    private Integer id;
    private String userLogin;
    @NotEmpty
    private String title;
    private MeetingCategory meetingCategory;
    private RecipientCategory recipientCategory;
    private String startDate;
    private LocalTime startTime;
    private String finishDate;
    private LocalTime finishTime;
    private String description;
    private Boolean facebookPublish;
    private Boolean messengerPublish;
    private Boolean emailPublish;
    private Status status;
    private String place;
}
