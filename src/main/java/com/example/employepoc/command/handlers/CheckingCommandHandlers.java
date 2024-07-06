package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.domain.CheckingAggregate;
import com.example.employepoc.command.exceptions.CheckingCreatedException;
import com.example.employepoc.command.exceptions.CheckingDeletedException;
import com.example.employepoc.command.exceptions.PersonNotFoundException;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.service.CheckingCommandService;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        try {
            Checking newChecking = checkingCommandService.createChecking(localDateTime, personId, cd, s, others);
            // Create a new CheckingAggregate from the new Checking
            createCheckingCommand.getChecking().setId(newChecking.getId());
            CheckingAggregate checkingAggregate = new CheckingAggregate(createCheckingCommand);

            // Save the new CheckingAggregate using the eventSourceHandler
            eventSourceHandler.save(checkingAggregate);
        }
        catch (PersonNotFoundException e) {
            log.error("Error creating checking record: " + e.getMessage());
            throw e;
        }
        catch (CheckingCreatedException e){
            throw e;
        }

    }

    @Override
    public void handle(CreateOrUpdatePersonCheckingCommand createOrUpdatePersonCheckingCommand) {
        // Extract the necessary information from the command object
        String id = createOrUpdatePersonCheckingCommand.getCheckingId();
        Long personId = createOrUpdatePersonCheckingCommand.getPersonId();
        LocalDateTime date = createOrUpdatePersonCheckingCommand.getDate().toDateTimeAtStartOfDay().toLocalDateTime();
        String threeDaysTime = createOrUpdatePersonCheckingCommand.getThreeDaysTime();
        try {
            Checking newChecking = checkingCommandService.createOrUpdatePersonChecking(id, personId, date, threeDaysTime);
        }
        catch (PersonNotFoundException e) {
            log.error("Error creating or updating checking record: " + e.getMessage());
            throw e;
        }
        // Create a new CheckingAggregate from the new Checking
        CheckingAggregate checkingAggregate = new CheckingAggregate(createOrUpdatePersonCheckingCommand);

        // Save the new CheckingAggregate using the eventSourceHandler
        eventSourceHandler.save(checkingAggregate);

    }
    /**
     * Handles the deletion of a person checking record.
     * @param deletePersonCheckingCommand Command containing the details for deleting a person checking record.
     */
    @Override
    public void handle(DeletePersonCheckingCommand deletePersonCheckingCommand) {
        Checking checking = deletePersonCheckingCommand.getChecking();
        boolean duplicate = deletePersonCheckingCommand.isDuplicate();
        try {
            checkingCommandService.deletePersonChecking(checking, duplicate);
        }
        catch (PersonNotFoundException e) {
            log.error("Error deleting checking record: " + e.getMessage());
            throw e;
        }
        catch (CheckingDeletedException e)
        {
            throw e;
        }
        CheckingAggregate checkingAggregate = new CheckingAggregate(deletePersonCheckingCommand);
        eventSourceHandler.save(checkingAggregate);

    }
    /**
     * Handles the creation of checking records for multiple persons.
     * @param createPersonsCheckingCommand Command containing the details for creating checking records for multiple persons.
     */
    @Override
    public void handle(CreatePersonsCheckingCommand createPersonsCheckingCommand) {
        // Extract the necessary information from the command object
      List<Long> personIds = createPersonsCheckingCommand.getPersonIds();
        LocalDate date = createPersonsCheckingCommand.getDate();
        String threeDaysTime = createPersonsCheckingCommand.getThreeDaysTime();
        try {
          List<Checking> checkings =   checkingCommandService.createPersonsChecking(personIds, date, threeDaysTime);
            createPersonsCheckingCommand.setCheckings(checkings);
            // Create a new CheckingAggregate from the new Checking
            CheckingAggregate checkingAggregate = new CheckingAggregate(createPersonsCheckingCommand);

            // Save the new CheckingAggregate using the eventSourceHandler
            eventSourceHandler.save(checkingAggregate);
        }
        catch (PersonNotFoundException e) {
            log.error("Error creating checking records: " + e.getMessage());
            throw e;
        }



    }
    /**
     * Handles the creation of checking records for multiple persons with collective information.
     * @param createPersonsCheckingWithCollectiveCommand Command containing the details for creating checking records for multiple persons with additional collective information.
     */
    @Override
    public void handle(CreatePersonsCheckingWithCollectiveCommand createPersonsCheckingWithCollectiveCommand) {
        // Extract the necessary information from the command object
        List<Long> personIds = createPersonsCheckingWithCollectiveCommand.getPersonIds();
        LocalDate date = createPersonsCheckingWithCollectiveCommand.getDate();
        String threeDaysTime = createPersonsCheckingWithCollectiveCommand.getThreeDaysTime();
        boolean collective = createPersonsCheckingWithCollectiveCommand.isCollective();
        try {
          List<Checking> checkings =  checkingCommandService.createPersonsChecking(personIds, date, threeDaysTime, collective);
          createPersonsCheckingWithCollectiveCommand.setCheckings(checkings);
            // Create a new CheckingAggregate from the new Checking
            CheckingAggregate checkingAggregate = new CheckingAggregate(createPersonsCheckingWithCollectiveCommand);

            // Save the new CheckingAggregate using the eventSourceHandler
            eventSourceHandler.save(checkingAggregate);
        }
        catch (PersonNotFoundException e) {
            log.error("Error creating checking records: " + e.getMessage());
            throw e;
        }


    }
}
