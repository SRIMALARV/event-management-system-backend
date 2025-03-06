
package com.trustrace.eventmanagementauth.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
        import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Data
@Document(collection = "events")
public class Event {

    @Id
    private String id;

    public String getId() {
        return id;
    }

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
    private LocalTime eventTime;

    private Number eventDuration;

    @NotNull
    private LocalDate registrationDeadline;

    @NotBlank
    private String eventMode;

    @NotBlank
    private String contactDetails;

    private String location;
    private Double fee;

    public @NotBlank @Size(min = 5) String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank @Size(min = 5) String title) {
        this.title = title;
    }

    @NotNull
    @Min(value = 1)
    private int minParticipants;

    private int maxParticipants;

    @NotBlank
    @Size(min = 10)
    private String instituteName;

    private String meetUrl;

    private String meetId;

    private String meetPasscode;

    private String status = "pending";

    @NotBlank
    private String createdBy;

    @NotBlank
    private String creatorEmail;

    public void setId(String id) {
        this.id = id;
    }

    public @NotBlank String getType() {
        return type;
    }

    public void setType(@NotBlank String type) {
        this.type = type;
    }

    public @NotBlank @Size(min = 50) String getDescription() {
        return description;
    }

    public void setDescription(@NotBlank @Size(min = 50) String description) {
        this.description = description;
    }

    public @NotNull @Future LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(@NotNull @Future LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public @NotNull LocalTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(@NotNull LocalTime eventTime) {
        this.eventTime = eventTime;
    }

    public Number getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Number eventDuration) {
        this.eventDuration = eventDuration;
    }

    public @NotNull LocalDate getRegistrationDeadline() {
        return registrationDeadline;
    }

    public void setRegistrationDeadline(@NotNull LocalDate registrationDeadline) {
        this.registrationDeadline = registrationDeadline;
    }

    public @NotBlank String getEventMode() {
        return eventMode;
    }

    public void setEventMode(@NotBlank String eventMode) {
        this.eventMode = eventMode;
    }

    public @NotBlank String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(@NotBlank String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    @NotNull
    @Min(value = 1)
    public int getMinParticipants() {
        return minParticipants;
    }

    public void setMinParticipants(@NotNull @Min(value = 1) int minParticipants) {
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public @NotBlank @Size(min = 10) String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(@NotBlank @Size(min = 10) String instituteName) {
        this.instituteName = instituteName;
    }

    public String getMeetUrl() {
        return meetUrl;
    }

    public void setMeetUrl(String meetUrl) {
        this.meetUrl = meetUrl;
    }

    public String getMeetId() {
        return meetId;
    }

    public void setMeetId(String meetId) {
        this.meetId = meetId;
    }

    public String getMeetPasscode() {
        return meetPasscode;
    }

    public void setMeetPasscode(String meetPasscode) {
        this.meetPasscode = meetPasscode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public @NotBlank String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(@NotBlank String createdBy) {
        this.createdBy = createdBy;
    }

    public @NotBlank String getCreatorEmail() {
        return creatorEmail;
    }

    public void setCreatorEmail(@NotBlank String creatorEmail) {
        this.creatorEmail = creatorEmail;
    }

}
