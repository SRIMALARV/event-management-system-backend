package com.trustrace.eventmanagementauth.models;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    @NotBlank
    @Size(min = 5)
    private String title;

    @NotBlank
    private String type;

    @NotBlank
    @Size(min = 50)
    private String description;

    @NotNull
    @Future
    private LocalDate eventDate;

    @NotNull
    private LocalDate registrationDeadline;

    @NotBlank
    private String eventMode;

    @NotBlank
    private String contactDetails;

    private String location;
    private Double fee;

    @Min(value = 1)
    private int minParticipants;

    private int maxParticipants;

    @NotBlank
    @Size(min = 10)
    private String instituteName;

    private String imageUrl;
}
