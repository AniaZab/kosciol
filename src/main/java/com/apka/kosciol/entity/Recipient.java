package com.apka.kosciol.entity;

import com.apka.kosciol.entity.User;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Recipient")
public class Recipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_recipient", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_user")
    private User userUser;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "firstName", length = 50)
    private String firstName;

    @Column(name = "lastName", length = 50)
    private String lastName;

    @Column(name = "active")
    private Boolean active;
}