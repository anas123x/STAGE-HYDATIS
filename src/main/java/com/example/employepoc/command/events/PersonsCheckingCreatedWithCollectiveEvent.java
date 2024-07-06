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
 * Event representing the creation of checking records for multiple persons with collective information.
 * This event is triggered when checking records for multiple persons are created collectively,
 * capturing essential details such as the date of the event, the persons involved, and whether the event is collective.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize
public class PersonsCheckingCreatedWithCollectiveEvent extends BaseEvent {
    private String identifier; // Unique identifier for the event

    private List<Long> personIds; // IDs of persons involved in the checking
    private LocalDate date; // Date of the checking event
    private String threeDaysTime; // Custom field, possibly indicating a time span or specific timing
    private boolean collective; // Flag indicating if the checking is a collective action
    private List<Checking> checkings; // List of checking records created
}