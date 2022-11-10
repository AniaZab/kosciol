package com.apka.kosciol.dto;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class UserDto {
        @NotNull
        private String login;
        @NotNull
        private String password;
        private String email;
        private String firstName;
        private String lastName;
}
