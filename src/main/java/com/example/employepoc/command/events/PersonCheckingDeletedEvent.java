package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Checking;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Event representing the deletion of a person's checking record.
 * This event is triggered when a checking record associated with a person is deleted,
 * capturing the unique identifier of the event, the checking details, and a flag indicating if it's a duplicate action.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize
public class PersonCheckingDeletedEvent extends BaseEvent {
    private String identifier;

    private Checking checking;
    private boolean duplicate;
}