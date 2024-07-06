package com.example.employepoc.command.domain;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.events.*;
import com.hydatis.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Represents the aggregate root for the Checking domain.
 * This class encapsulates the state and the changes of the Checking domain by handling commands and applying events.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAggregate extends AggregateRoot {
    private String id;
    /**
     * Constructor that handles the creation of a checking record.
     * It raises a CreateCheckingEvent based on the provided CreateCheckingCommand.
     *
     * @param command The command containing the necessary information to create a checking record.
     */
    public CheckingAggregate(CreateCheckingCommand command) {
        raiseEvent(CreateCheckingEvent.builder()
                .id(command.getId())
                .personId(command.getPersonId())
                .checking(command.getChecking())
                .others(command.getOthers())
                .build());
    }
    /**
     * Applies the CreateCheckingEvent to the aggregate.
     * This method updates the aggregate's state based on the event's data.
     *
     * @param event The event to apply to the aggregate.
     */
    public void apply(CreateCheckingEvent event) {
        this.id = event.getIdentifier();
    }

    /**
     * Constructor that handles the creation or update of a person's checking record.
     * It raises a PersonCheckingCreatedOrUpdatedEvent based on the provided CreateOrUpdatePersonCheckingCommand.
     *
     * @param command The command containing the necessary information to create or update a person's checking record.
     */
    public CheckingAggregate(CreateOrUpdatePersonCheckingCommand command) {
        raiseEvent(PersonCheckingCreatedOrUpdatedEvent.builder()
                .id(command.getId())
                .personId(command.getPersonId())
                .date(command.getDate())
                .checkingId(command.getCheckingId())
                .threeDaysTime(command.getThreeDaysTime())
                .build());
    }

    public void apply(PersonCheckingCreatedOrUpdatedEvent event) {
        this.id = event.getIdentifier();
    }
    /**
     * Constructor that handles the deletion of a person's checking record.
     * It raises a PersonCheckingDeletedEvent based on the provided DeletePersonCheckingCommand.
     *
     * @param command The command containing the necessary information to delete a person's checking record.
     */
    public CheckingAggregate(DeletePersonCheckingCommand command) {
        raiseEvent(PersonCheckingDeletedEvent.builder()
                .checking(command.getChecking())
                .duplicate(command.isDuplicate())
                .build());
    }

    public void apply(PersonCheckingDeletedEvent event) {
        this.id = event.getIdentifier();
    }

    public CheckingAggregate(CreatePersonsCheckingCommand command) {
        raiseEvent(PersonsCheckingCreatedEvent.builder()
                .personIds(command.getPersonIds())
                .date(command.getDate())
                .threeDaysTime(command.getThreeDaysTime())
                .checkings(command.getCheckings())
                .build());
    }

    public void apply(PersonsCheckingCreatedEvent event) {
        this.id = event.getIdentifier();
    }

    public CheckingAggregate(CreatePersonsCheckingWithCollectiveCommand command) {
        raiseEvent(PersonsCheckingCreatedWithCollectiveEvent.builder()
                .personIds(command.getPersonIds())
                .date(command.getDate())
                .threeDaysTime(command.getThreeDaysTime())
                .collective(command.isCollective())
                .checkings(command.getCheckings())
                .build());
    }

    public void apply(PersonsCheckingCreatedWithCollectiveEvent event) {
        this.id = event.getIdentifier();
    }
}