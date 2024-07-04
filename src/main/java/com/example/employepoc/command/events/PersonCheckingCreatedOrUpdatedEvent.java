package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Checking;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize

/**
 * Event representing the deletion of a person's checking record.
 * This event is triggered when a checking record associated with a person is deleted,
 * capturing the unique identifier of the event, the checking details, and a flag indicating if it's a duplicate action.
 */
public class PersonCheckingCreatedOrUpdatedEvent extends BaseEvent {
    private String identifier;

    //private Long id;
    private Long personId;
    private LocalDate date;
    private String threeDaysTime;
}