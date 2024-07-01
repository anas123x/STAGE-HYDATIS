package com.example.employepoc.command.rest.controllers;

import com.example.employepoc.command.commands.CreateCheckingCommand;
import com.example.employepoc.command.commands.DeletePersonCheckingCommand;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.requests.CreateCheckingRequest;
import com.example.employepoc.command.rest.requests.DeletePersonCheckingRequest;
import com.hydatis.cqrsref.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checkings")
@RequiredArgsConstructor
public class CheckingController {

    private final CommandDispatcher commandDispatcher;

    @PostMapping
    public void saveChecking(@RequestBody CreateCheckingRequest checkingRequest) {
        Checking checking = checkingRequest.getChecking();
        Long personId = checkingRequest.getPersonId();
        ArrayList<Checking> others = checkingRequest.getOthers();

        System.out.println("personId: " + personId + " checking: " + checking + " others: " + others);

        CreateCheckingCommand createCheckingCommand = new CreateCheckingCommand();
        createCheckingCommand.setId(UUID.randomUUID().toString());
        createCheckingCommand.setPersonId(personId);
        createCheckingCommand.setChecking(checking);

        createCheckingCommand.setOthers(others);

        commandDispatcher.send(createCheckingCommand);
    }

    @DeleteMapping
    public void deleteChecking(@RequestBody DeletePersonCheckingRequest deletePersonCheckingRequest) {
        // Create a new DeletePersonCheckingCommand

        DeletePersonCheckingCommand dpchc =   new DeletePersonCheckingCommand() ;
        dpchc.setChecking(deletePersonCheckingRequest.getChecking());
        dpchc.setDuplicate(deletePersonCheckingRequest.isDuplicate());
        commandDispatcher.send(dpchc);


    }
}

