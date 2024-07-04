package com.example.employepoc.query.events;

import com.example.employepoc.command.events.*;

/**
 * Interface for handling events related to checkings.
 * This interface defines the contract for processing various checking-related events,
 * including the creation, update, and deletion of checkings, as well as handling collective checking events.
 */
public interface CheckingEvenHandlerInterface {
    /**
     * Handles the event when a checking is created.
     *
     * @param event The event containing the details of the created checking.
     */
    void on(CreateCheckingEvent event);

    /**
     * Handles the event when a person's checking is created or updated.
     *
     * @param event The event containing the details of the created or updated checking.
     */
    void on(PersonCheckingCreatedOrUpdatedEvent event);

    /**
     * Handles the event when a person's checking is deleted.
     *
     * @param event The event containing the details of the deleted checking.
     */
    void on(PersonCheckingDeletedEvent event);

    /**
     * Handles the event when checkings for multiple persons are created collectively.
     *
     * @param event The event containing the details of the collectively created checkings.
     */
    void on(PersonsCheckingCreatedWithCollectiveEvent event);

    /**
     * Handles the event when checkings for multiple persons are created.
     *
     * @param event The event containing the details of the created checkings for multiple persons.
     */
    void on(PersonsCheckingCreatedEvent event);
}