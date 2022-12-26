package com.apka.kosciol.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class PasswordDto {
    @NotEmpty
    private String newPassword;
    @NotEmpty
    private String previousPassword;
}
