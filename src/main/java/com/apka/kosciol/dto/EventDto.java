package com.apka.kosciol.dto;

import com.apka.kosciol.entity.MeetingCategory;
import com.apka.kosciol.entity.RecipientCategory;
import com.apka.kosciol.entity.Status;
import com.apka.kosciol.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class EventDto {

    private String userLogin;
    @NotEmpty
    private String title;
    private MeetingCategory meetingCategory;
    private RecipientCategory recipientCategory;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate finishDate;
    private LocalTime finishTime;
    private String description;
    private Boolean facebookPublish;
    private Boolean messengerPublish;
    private Boolean emailPublish;
    private Status status;
}
