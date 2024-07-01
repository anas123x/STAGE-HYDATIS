package com.example.employepoc.command.domain;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.events.*;
import com.hydatis.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckingAggregate extends AggregateRoot {
    private String id;

    public CheckingAggregate(CreateCheckingCommand command) {
        raiseEvent(CreateCheckingEvent.builder()
                .id(command.getId())
                .personId(command.getPersonId())
                .checking(command.getChecking())
                .others(command.getOthers())
                .build());
    }

    public void apply(CreateCheckingEvent event) {
        this.id = event.getIdentifier();
    }

    public CheckingAggregate(CreateOrUpdatePersonCheckingCommand command) {
        raiseEvent(PersonCheckingCreatedOrUpdatedEvent.builder()
                .id(command.getId())
                .personId(command.getPersonId())
                .date(command.getDate())
                .threeDaysTime(command.getThreeDaysTime())
                .build());
    }

    public void apply(PersonCheckingCreatedOrUpdatedEvent event) {
        this.id = event.getIdentifier();
    }

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
                .build());
    }

    public void apply(PersonsCheckingCreatedWithCollectiveEvent event) {
        this.id = event.getIdentifier();
    }
}