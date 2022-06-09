package com.apka.kosciol.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class LiveStream {
    private String id;
    private String title;
    private String description;
    private String url;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
