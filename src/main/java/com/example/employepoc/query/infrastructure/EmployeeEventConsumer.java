package com.example.employepoc.query.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * Service component responsible for consuming Kafka messages related to employee events.
 * Implements {@link EmployeeEventConsumerInterface} to define specific event handling methods.
 */
@Service
public class EmployeeEventConsumer implements EmployeeEventConsumerInterface {
    private final EmployeeEventHandler handler;

    /**
     * Constructs an EmployeeEventConsumer with a specified {@link EmployeeEventHandler}.
     *
     * @param handler The event handler responsible for processing employee events.
     */
    @Autowired
    public EmployeeEventConsumer(EmployeeEventHandler handler) {
        this.handler = handler;
    }

    /**
     * Consumes and processes {@link EmployeeCreatedEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The employee created event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "EmployeeCreatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(EmployeeCreatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes and processes {@link EmployeeUpdatedEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The employee updated event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "EmployeeUpdatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(EmployeeUpdatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes and processes {@link EmployeeDeletedEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The employee deleted event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "EmployeeDeletedEvent", groupId = "employee-consumer")
    @Override
    public void consume(EmployeeDeletedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
}