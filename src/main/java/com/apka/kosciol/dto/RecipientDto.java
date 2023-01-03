package com.apka.kosciol.dto;

import com.apka.kosciol.entity.MeetingCategory;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class RecipientDto {
    private Integer id;
    private String userLogin;
    @NotEmpty
    private String email;
    private String firstName;
    private String lastName;
    private Boolean active;
    private List<MeetingCategory> meetingCategoryList;
}
