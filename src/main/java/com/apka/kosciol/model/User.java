package com.apka.kosciol.model;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String login;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String role;
}
