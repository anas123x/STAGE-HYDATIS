package com.example.employepoc.command.rest.controllers;

import com.example.employepoc.command.commands.CreateCheckingCommand;
import com.example.employepoc.command.commands.DeletePersonCheckingCommand;
import com.example.employepoc.command.exceptions.CheckingCreatedException;
import com.example.employepoc.command.exceptions.CheckingDeletedException;
import com.example.employepoc.command.exceptions.PersonNotFoundException;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.requests.CreateCheckingRequest;
import com.example.employepoc.command.rest.requests.DeletePersonCheckingRequest;
import com.example.employepoc.command.rest.response.CheckingResponse;
import com.hydatis.cqrsref.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * Controller for handling checking-related operations.
 * This controller provides endpoints for creating and deleting checking records.
 */
@RestController
@RequestMapping("/api/checkings")
@RequiredArgsConstructor
public class CheckingController {

    private final CommandDispatcher commandDispatcher;

    /**
     * Endpoint to save a new checking record.
     * Accepts a request to create a new checking record, along with any additional related checkings,
     * and dispatches a command to handle the creation.
     *
     * @param checkingRequest The request containing the checking details to be saved.
     */
    @PostMapping
    public ResponseEntity<CheckingResponse> saveChecking(@RequestBody CreateCheckingRequest checkingRequest) {
        Checking checking = checkingRequest.getChecking();
        Long personId = checkingRequest.getPersonId();
        ArrayList<Checking> others = checkingRequest.getOthers();

        System.out.println("personId: " + personId + " checking: " + checking + " others: " + others);

        CreateCheckingCommand createCheckingCommand = new CreateCheckingCommand();
        createCheckingCommand.setId(UUID.randomUUID().toString());
        createCheckingCommand.setPersonId(personId);
        createCheckingCommand.setChecking(checking);
        createCheckingCommand.setOthers(others);
        try {
            commandDispatcher.send(createCheckingCommand);
        }
        catch (PersonNotFoundException e){
            return ResponseEntity.status(404).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        }
        catch (CheckingCreatedException e){
            return ResponseEntity.status(500).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        }

        return ResponseEntity.status(201).body(new CheckingResponse().builder()
                .checking(checking)
                .message("Checking saved successfully").
                build());
    }

    /**
     * Endpoint to delete a checking record.
     * Accepts a request to delete a specific checking record and dispatches a command to handle the deletion.
     *
     * @param deletePersonCheckingRequest The request containing the checking details to be deleted.
     */
    @DeleteMapping
    public ResponseEntity<CheckingResponse>  deleteChecking(@RequestBody DeletePersonCheckingRequest deletePersonCheckingRequest) {
        // Create a new DeletePersonCheckingCommand

        DeletePersonCheckingCommand dpchc = new DeletePersonCheckingCommand();
        dpchc.setChecking(deletePersonCheckingRequest.getChecking());
        dpchc.setDuplicate(deletePersonCheckingRequest.isDuplicate());
        try {
            commandDispatcher.send(dpchc);
        }

        catch (PersonNotFoundException | CheckingDeletedException e){
        return ResponseEntity.status(404).body(new CheckingResponse().builder()
                .message(e.getMessage())
                .build());
    }
        return ResponseEntity.ok(new CheckingResponse().builder()
                .checking(deletePersonCheckingRequest.getChecking())
                .message("Checking deleted successfully").
                build());
    }
}