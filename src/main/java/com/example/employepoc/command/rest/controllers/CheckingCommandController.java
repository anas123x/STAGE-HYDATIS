package com.example.employepoc.command.rest.controllers;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.exceptions.PersonNotFoundException;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.requests.*;
import com.example.employepoc.command.rest.response.CheckingResponse;
import com.hydatis.cqrsref.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Controller for handling checking-related operations.
 * This controller provides endpoints for creating, updating, deleting, and managing checking records.
 * It interacts with the CommandDispatcher to process commands related to checking operations.
 */
@RestController
@RequestMapping("/checking")
@RequiredArgsConstructor
public class CheckingCommandController {

    private final CommandDispatcher commandDispatcher;

    /**
     * Creates a new checking record.
     * This endpoint accepts a request to create a new checking record and dispatches a command to handle the creation.
     * It returns a response entity containing the created checking record and a success message.
     *
     * @param checkingRequest The request containing the checking details to be saved.
     * @return ResponseEntity containing the created checking and a success message.
     */
    @PostMapping("/create")
    public ResponseEntity<CheckingResponse> saveChecking(@RequestBody CreateCheckingRequest checkingRequest) {
        try {
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
            return ResponseEntity.status(201).body(new CheckingResponse().builder()
                    .checking(checking)
                    .message("Checking saved successfully").
                    build());
        }
        catch (PersonNotFoundException e){
            return ResponseEntity.status(404).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        }


        catch(Exception e){
            return ResponseEntity.status(400).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());

        }


    }

    /**
     * Deletes a specific checking record.
     * This endpoint accepts a request to delete a checking record based on the provided details and dispatches a command for deletion.
     * It returns a response entity with a success message upon successful deletion.
     *
     * @param deletePersonCheckingRequest The request containing the checking details to be deleted.
     * @return ResponseEntity containing a success message upon deletion.
     */
    @DeleteMapping("/delete")
    public ResponseEntity<CheckingResponse>  deleteChecking(@RequestBody DeletePersonCheckingRequest deletePersonCheckingRequest) {
        // Create a new DeletePersonCheckingCommand

        DeletePersonCheckingCommand dpchc = new DeletePersonCheckingCommand();
        dpchc.setChecking(deletePersonCheckingRequest.getChecking());
        dpchc.setDuplicate(deletePersonCheckingRequest.isDuplicate());
        try {
            commandDispatcher.send(dpchc);
        }

        catch (PersonNotFoundException e){
        return ResponseEntity.status(404).body(new CheckingResponse().builder()
                .message(e.getMessage())
                .build());
    }
        return ResponseEntity.ok(new CheckingResponse().builder()
                .checking(deletePersonCheckingRequest.getChecking())
                .message("Checking deleted successfully").
                build());
    }
    /**
     * Creates or updates a checking record.
     * This endpoint handles the creation or update of a checking record based on the provided details.
     * It dispatches a command to either create a new record or update an existing one.
     * A success message is returned in the response entity.
     *
     * @param checkingRequest The request containing the checking details for creation or update.
     * @return ResponseEntity containing a success message upon creation or update.
     */
    @PostMapping("/createOrUpdate")
    public ResponseEntity<CheckingResponse> createOrUpdateChecking(@RequestBody CreateOrUpdateCheckingRequest checkingRequest) {
        CreateOrUpdatePersonCheckingCommand checkingCommand = new CreateOrUpdatePersonCheckingCommand();
        checkingCommand.setCheckingId(checkingRequest.getCheckingId());
        checkingCommand.setPersonId(checkingRequest.getPersonId());
        checkingCommand.setDate(checkingRequest.getDate());
        checkingCommand.setThreeDaysTime(checkingRequest.getThreeDaysTime());

        try {
            commandDispatcher.send(checkingCommand);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(404).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        }

        return ResponseEntity.status(201).body(new CheckingResponse().builder()
                .message("Checking created or updated successfully")
                .build());
    }

    /**
     * Creates multiple checking records.
     * This endpoint accepts a request to create multiple checking records at once and dispatches a command to process the creation.
     * A success message is returned in the response entity upon successful creation.
     *
     * @param checkingRequest The request containing the details for creating multiple checking records.
     * @return ResponseEntity containing a success message upon successful creation of multiple records.
     */
    @PostMapping("/createMultiple")
    public ResponseEntity<CheckingResponse> createMultipleCheckings(@RequestBody CreatePersonsCheckingRequest checkingRequest) {
        CreatePersonsCheckingCommand CPCR = new CreatePersonsCheckingCommand();
        CPCR.setPersonIds( checkingRequest.getPersonIds());
        CPCR.setDate(checkingRequest.getDate());
        CPCR.setThreeDaysTime(checkingRequest.getThreeDaysTime());

        try {
            commandDispatcher.send(CPCR);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(404).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        }

        return ResponseEntity.status(201).body(new CheckingResponse().builder()
                .message("Checkings created successfully")

                .build());
    }

    /**
     * Creates multiple checking records with a collective option.
     * This endpoint allows for the creation of multiple checking records with an option to mark them as collective.
     * It dispatches a command to handle the creation of these records with the specified collective attribute.
     * A success message is returned in the response entity upon successful creation.
     *
     * @param checkingRequest The request containing the details for creating multiple checking records with a collective option.
     * @return ResponseEntity containing a success message upon successful creation.
     */
    @PostMapping("/createMultipleWithCollective")
    public ResponseEntity<CheckingResponse> createMultipleCheckingsWithCollective(@RequestBody CreatePersonsCheckingWithCollectiveRequest checkingRequest) {
        CreatePersonsCheckingWithCollectiveCommand CPCR = new CreatePersonsCheckingWithCollectiveCommand();
        CPCR.setPersonIds( checkingRequest.getPersonIds());
        CPCR.setDate(checkingRequest.getDate());
        CPCR.setThreeDaysTime(checkingRequest.getThreeDaysTime());
        CPCR.setCollective(checkingRequest.isCollective());

        try {
            commandDispatcher.send(CPCR);
        } catch (PersonNotFoundException e) {
            return ResponseEntity.status(404).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(500).body(new CheckingResponse().builder()
                    .message(e.getMessage())
                    .build());
        }

        return ResponseEntity.status(201).body(new CheckingResponse().builder()
                .message("Collective checkings created successfully")
                .build());
    }



}