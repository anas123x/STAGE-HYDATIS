package com.example.employepoc.query.infrastructure;

import com.example.employepoc.command.events.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Interface defining the contract for consuming checking-related events.
 * This interface specifies methods for handling various types of checking events,
 * including creation, update, and deletion of checkings for persons and collective checkings.
 * Implementations of this interface are responsible for processing these events accordingly.
 */
public interface CheckingEventConsumerInterface {
    /**
     * Consumes an event indicating that a checking has been created.
     *
     * @param event The {@link CreateCheckingEvent} instance containing the details of the created checking.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload CreateCheckingEvent event, Acknowledgment ack);

    /**
     * Consumes an event indicating that a person's checking has been created or updated.
     *
     * @param event The {@link PersonCheckingCreatedOrUpdatedEvent} instance containing the details of the created or updated checking.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload PersonCheckingCreatedOrUpdatedEvent event, Acknowledgment ack);

    /**
     * Consumes an event indicating that a person's checking has been deleted.
     *
     * @param event The {@link PersonCheckingDeletedEvent} instance containing the details of the deleted checking.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload PersonCheckingDeletedEvent event, Acknowledgment ack);

    /**
     * Consumes an event indicating that checkings for multiple persons have been created collectively.
     *
     * @param event The {@link PersonsCheckingCreatedWithCollectiveEvent} instance containing the details of the collectively created checkings.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload PersonsCheckingCreatedWithCollectiveEvent event, Acknowledgment ack);

    /**
     * Consumes an event indicating that checkings for multiple persons have been created.
     *
     * @param event The {@link PersonsCheckingCreatedEvent} instance containing the details of the created checkings for multiple persons.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload PersonsCheckingCreatedEvent event, Acknowledgment ack);
}