package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.domain.CheckingAggregate;
import com.example.employepoc.command.domain.EmployeeAggregate;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.service.CheckingCommandService;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@ComponentScan("com.example.employepoc.command.rest.repository")
@ComponentScan("com.example.employepoc.command.infrastructure")
public class CheckingCommandHandlers implements CheckingCommandHandlersInterface {
    private final EventSourcingHandler<CheckingAggregate> eventSourceHandler;
    private final CheckingCommandService checkingCommandService;

    public CheckingCommandHandlers(EventSourcingHandler<CheckingAggregate> eventSourceHandler, CheckingCommandService checkingCommandService) {
        this.eventSourceHandler = eventSourceHandler;
        this.checkingCommandService = checkingCommandService;
    }

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

    @Override
    public void handle(DeletePersonCheckingCommand deletePersonCheckingCommand) {
        Checking checking = deletePersonCheckingCommand.getChecking();
        boolean duplicate = deletePersonCheckingCommand.isDuplicate();
        checkingCommandService.deletePersonChecking(checking, duplicate);
        CheckingAggregate checkingAggregate = new CheckingAggregate(deletePersonCheckingCommand);
        eventSourceHandler.save(checkingAggregate);

    }

    @Override
    public void handle(CreatePersonsCheckingCommand createPersonsCheckingCommand) {

    }

    @Override
    public void handle(CreatePersonsCheckingWithCollectiveCommand createPersonsCheckingWithCollectiveCommand) {

    }
}
