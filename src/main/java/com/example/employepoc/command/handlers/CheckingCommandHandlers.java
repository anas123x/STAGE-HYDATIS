package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.domain.CheckingAggregate;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.service.CheckingCommandService;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Service layer responsible for handling commands related to checking operations.
 * Implements the {@link CheckingCommandHandlersInterface} to process various checking-related commands.
 */
@Slf4j
@Service
@ComponentScan("com.example.employepoc.command.rest.repository")
@ComponentScan("com.example.employepoc.command.infrastructure")
public class CheckingCommandHandlers implements CheckingCommandHandlersInterface {
    private final EventSourcingHandler<CheckingAggregate> eventSourceHandler;
    private final CheckingCommandService checkingCommandService;
    /**
     * Constructs a CheckingCommandHandlers service with necessary dependencies.
     * @param eventSourceHandler Handles the persistence of CheckingAggregate events.
     * @param checkingCommandService Service for checking command operations.
     */
    public CheckingCommandHandlers(EventSourcingHandler<CheckingAggregate> eventSourceHandler, CheckingCommandService checkingCommandService) {
        this.eventSourceHandler = eventSourceHandler;
        this.checkingCommandService = checkingCommandService;
    }

    /**
     * Handles the creation of a new checking record.
     * Extracts information from the command, creates a new checking record, and persists the event.
     * @param createCheckingCommand Command containing the details for creating a new checking record.
     */
    @Override
    public void handle(CreateCheckingCommand createCheckingCommand) {
        // Extract the necessary information from the command object
        LocalDateTime localDateTime = createCheckingCommand.getChecking().getActualTime();
        Long personId = createCheckingCommand.getPersonId();
        Checking.CheckingDirection cd = createCheckingCommand.getChecking().getDirection();
        Checking.CheckingSource s = createCheckingCommand.getChecking().getActualSource();

        ArrayList<Checking> others = createCheckingCommand.getOthers();

        Checking newChecking = checkingCommandService.createChecking(localDateTime, personId, cd, s, others);

        // Create a new CheckingAggregate from the new Checking
        CheckingAggregate checkingAggregate = new CheckingAggregate(createCheckingCommand);

        // Save the new CheckingAggregate using the eventSourceHandler
        eventSourceHandler.save(checkingAggregate);
    }

    @Override
    public void handle(CreateOrUpdatePersonCheckingCommand createOrUpdatePersonCheckingCommand) {

    }
    /**
     * Handles the deletion of a person checking record.
     * @param deletePersonCheckingCommand Command containing the details for deleting a person checking record.
     */
    @Override
    public void handle(DeletePersonCheckingCommand deletePersonCheckingCommand) {
        Checking checking = deletePersonCheckingCommand.getChecking();
        boolean duplicate = deletePersonCheckingCommand.isDuplicate();
        checkingCommandService.deletePersonChecking(checking, duplicate);
        CheckingAggregate checkingAggregate = new CheckingAggregate(deletePersonCheckingCommand);
        eventSourceHandler.save(checkingAggregate);

    }
    /**
     * Handles the creation of checking records for multiple persons.
     * @param createPersonsCheckingCommand Command containing the details for creating checking records for multiple persons.
     */
    @Override
    public void handle(CreatePersonsCheckingCommand createPersonsCheckingCommand) {

    }
    /**
     * Handles the creation of checking records for multiple persons with collective information.
     * @param createPersonsCheckingWithCollectiveCommand Command containing the details for creating checking records for multiple persons with additional collective information.
     */
    @Override
    public void handle(CreatePersonsCheckingWithCollectiveCommand createPersonsCheckingWithCollectiveCommand) {

    }
}
