package com.apka.kosciol.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(name = "meetingCategory", length = 50)
    private String meetingCategory;

    @Column(name = "recipientCategory", length = 50)
    private String recipientCategory;

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

    @Column(name = "status", length = 50)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getEmailPublish() {
        return emailPublish;
    }

    public void setEmailPublish(Boolean emailPublish) {
        this.emailPublish = emailPublish;
    }

    public Boolean getMessengerPublish() {
        return messengerPublish;
    }

    public void setMessengerPublish(Boolean messengerPublish) {
        this.messengerPublish = messengerPublish;
    }

    public Boolean getFacebookPublish() {
        return facebookPublish;
    }

    public void setFacebookPublish(Boolean facebookPublish) {
        this.facebookPublish = facebookPublish;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalTime finishTime) {
        this.finishTime = finishTime;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getRecipientCategory() {
        return recipientCategory;
    }

    public void setRecipientCategory(String recipientCategory) {
        this.recipientCategory = recipientCategory;
    }

    public String getMeetingCategory() {
        return meetingCategory;
    }

    public void setMeetingCategory(String meetingCategory) {
        this.meetingCategory = meetingCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}