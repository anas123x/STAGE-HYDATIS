package com.example.employepoc.query.infrastructure;

import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

/**
 * Interface defining the contract for consuming employee-related events.
 * This interface is used to handle events related to the creation, update, and deletion of employees.
 * Implementations of this interface will be responsible for processing these events accordingly.
 */
public interface EmployeeEventConsumerInterface {

    /**
     * Consumes an event indicating that an employee has been created.
     *
     * @param event The {@link EmployeeCreatedEvent} instance containing the details of the created employee.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload EmployeeCreatedEvent event, Acknowledgment ack);

    /**
     * Consumes an event indicating that an employee has been updated.
     *
     * @param event The {@link EmployeeUpdatedEvent} instance containing the updated details of the employee.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload EmployeeUpdatedEvent event, Acknowledgment ack);

    /**
     * Consumes an event indicating that an employee has been deleted.
     *
     * @param event The {@link EmployeeDeletedEvent} instance containing the details of the deleted employee.
     * @param ack   The {@link Acknowledgment} instance used to manually acknowledge message processing.
     */
    void consume(@Payload EmployeeDeletedEvent event, Acknowledgment ack);
}