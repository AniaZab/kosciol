package com.apka.kosciol.dto;

import javax.validation.constraints.NotEmpty;

public class RecipientDto {
    private Integer id;
    private String userLogin;
    @NotEmpty
    private String email;
    private String firstName;
    private String lastName;
    private Boolean active;
}
