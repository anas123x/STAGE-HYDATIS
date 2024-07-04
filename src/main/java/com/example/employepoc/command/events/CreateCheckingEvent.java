package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Checking;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents an event for creating a checking record.
 * This event is used to encapsulate the data required to create a new checking record,
 * including the unique identifier of the event, the ID of the person associated with the checking,
 * the primary checking details, and any additional checking records related to the same event.
 */
@Getter
@AllArgsConstructor // Generates constructor with all arguments
@NoArgsConstructor // Generates no-arguments constructor
@SuperBuilder // Provides a builder pattern for object construction
@JsonSerialize // Indicates that this class can be serialized into JSON
@ToString // Generates a toString method including all class attributes
public class CreateCheckingEvent extends BaseEvent {
    private String identifier; // Unique identifier for the event
    private Long personId; // ID of the person associated with the checking
    private Checking checking; // Primary checking details
    private ArrayList<Checking> others; // Additional checking records related to the event
}