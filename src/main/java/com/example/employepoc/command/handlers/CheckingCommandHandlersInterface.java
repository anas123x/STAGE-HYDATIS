package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.*;

/**
 * Interface defining the handlers for various checking-related commands.
 * This interface is responsible for defining the contract for handling commands
 * related to creating, updating, and deleting checking records and person checking records.
 */
public interface CheckingCommandHandlersInterface {
    /**
     * Handles the creation of a checking record.
     * @param createCheckingCommand The command containing the details for creating a checking record.
     */
    void handle(CreateCheckingCommand createCheckingCommand);

    /**
     * Handles the creation or update of a person checking record.
     * @param createOrUpdatePersonCheckingCommand The command containing the details for creating or updating a person checking record.
     */
    void handle(CreateOrUpdatePersonCheckingCommand createOrUpdatePersonCheckingCommand);

    /**
     * Handles the deletion of a person checking record.
     * @param deletePersonCheckingCommand The command containing the details for deleting a person checking record.
     */
    void handle(DeletePersonCheckingCommand deletePersonCheckingCommand);

    /**
     * Handles the creation of checking records for multiple persons.
     * @param createPersonsCheckingCommand The command containing the details for creating checking records for multiple persons.
     */
    void handle(CreatePersonsCheckingCommand createPersonsCheckingCommand);

    /**
     * Handles the creation of checking records for multiple persons with collective information.
     * @param createPersonsCheckingWithCollectiveCommand The command containing the details for creating checking records for multiple persons with additional collective information.
     */
    void handle(CreatePersonsCheckingWithCollectiveCommand createPersonsCheckingWithCollectiveCommand);
}