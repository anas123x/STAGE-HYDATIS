package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Checking;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Event representing the creation of checking records for multiple persons.
 * This event encapsulates the details necessary for identifying the checking event,
 * including the persons involved and the date of the event.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize
public class PersonsCheckingCreatedEvent extends BaseEvent {
    private String identifier; // Unique identifier for the event

    private List<String> personIds; // IDs of persons involved in the checking
    private LocalDate date; // Date of the checking event
    private String threeDaysTime; // Custom field, possibly indicating a time span or specific timing
    private List<Checking> checkings;
}