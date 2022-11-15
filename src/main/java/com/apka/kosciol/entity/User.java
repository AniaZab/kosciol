package com.apka.kosciol.entity;

import com.apka.kosciol.validations.PasswordMatches;
import com.apka.kosciol.validations.ValidEmail;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

//@PasswordMatches
@Data
@Entity
@Table(name="User_Account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @NotNull
    @Column(name = "login", nullable = false, length = 50)
    private String login;

    //@ValidEmail
    //@NotNull
    //@NotEmpty
    @Column(name = "email")
    private String email;

    @Column(name = "firstName", length = 50)
    private String firstName;

    @Column(name = "lastName", length = 50)
    private String lastName;

    @NotNull
    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "qtyOfWrongPassword")
    private Integer qtyOfWrongPassword;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 5)
    private Role role;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "changedPassword")
    private Boolean changedPassword;

    @OneToMany(mappedBy = "idUser")
    private Set<Event> events = new LinkedHashSet<>();
public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }


    /*
    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Integer getQtyOfWrongPassword() {
        return qtyOfWrongPassword;
    }

    public void setQtyOfWrongPassword(Integer qtyOfWrongPassword) {
        this.qtyOfWrongPassword = qtyOfWrongPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }*/



    //TODO Reverse Engineering! Migrate other columns to the entity
}