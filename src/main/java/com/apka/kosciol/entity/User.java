package com.apka.kosciol.entity;

import com.apka.kosciol.validations.PasswordMatches;
import com.apka.kosciol.validations.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @Column(name = "login", nullable = false, length = 50)
    private String login;

    //@ValidEmail
    @NotNull
    @NotEmpty
    @Column(name = "email")
    private String email;

    @Column(name = "firstName", length = 50)
    private String firstName;

    @Column(name = "lastName", length = 50)
    private String lastName;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "qtyOfWrongPassword")
    private Integer qtyOfWrongPassword;

    @Column(name = "role", nullable = false, length = 5)
    private String role;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "changedPassword")
    private Boolean changedPassword;

    public Boolean getChangedPassword() {
        return changedPassword;
    }

    public void setChangedPassword(Boolean changedPassword) {
        this.changedPassword = changedPassword;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
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
    }

    public String getMatchingPassword() {
    }

    //TODO Reverse Engineering! Migrate other columns to the entity
}