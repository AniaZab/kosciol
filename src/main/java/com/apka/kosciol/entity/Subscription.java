package com.apka.kosciol.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Subscription")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subscription", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JoinColumn(name = "id_recipient")
    private Recipient recipient;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_category", length = 50)
    private MeetingCategory meetingCategory;

}