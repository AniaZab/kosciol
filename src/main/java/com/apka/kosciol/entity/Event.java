package com.apka.kosciol.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "Event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_event", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "version")
    private Integer version;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "meetingCategory", length = 50)
    private MeetingCategory meetingCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipientCategory", length = 50)
    private RecipientCategory recipientCategory;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "finish_time")
    private LocalTime finishTime;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "facebookPublish", nullable = false)
    private Boolean facebookPublish = false;

    @Column(name = "messengerPublish", nullable = false)
    private Boolean messengerPublish = false;

    @Column(name = "emailPublish", nullable = false)
    private Boolean emailPublish = false;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50)
    private Status status;

}