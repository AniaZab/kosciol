package com.apka.kosciol.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
        private Integer id;
        @NotEmpty
        private String login;
        private String password;
        @NotEmpty
        private String email;
        private String firstName;
        private String lastName;
}
