package com.apka.kosciol.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserDto {
        @NotEmpty
        private String login;
        @NotEmpty
        private String password;
        @NotEmpty
        private String email;
        private String firstName;
        private String lastName;
}
