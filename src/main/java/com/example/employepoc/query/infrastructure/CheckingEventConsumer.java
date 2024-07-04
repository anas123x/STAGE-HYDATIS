package com.example.employepoc.query.infrastructure;

import com.example.employepoc.command.events.*;
import com.example.employepoc.query.events.CheckingEventHandler;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * Service component responsible for consuming Kafka messages related to checking events.
 * Implements {@link CheckingEventConsumerInterface} to define specific event handling methods.
 */
@Service
public class CheckingEventConsumer implements CheckingEventConsumerInterface {
    private final CheckingEventHandler handler;
    private static final Logger logger = LoggerFactory.getLogger(CheckingEventHandler.class);

    /**
     * Constructs a CheckingEventConsumer with a specified {@link CheckingEventHandler}.
     *
     * @param handler The event handler responsible for processing checking events.
     */
    @Autowired
    public CheckingEventConsumer(CheckingEventHandler handler) {
        this.handler = handler;
    }

    /**
     * Consumes and processes {@link CreateCheckingEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The create checking event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "CreateCheckingEvent", groupId = "employee-consumer")
    @Override
    public void consume(CreateCheckingEvent event, Acknowledgment ack) {
        logger.info("Checking created event received: {}", event);
        this.handler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes and processes {@link PersonCheckingCreatedOrUpdatedEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The person checking created or updated event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "PersonCheckingCreatedOrUpdatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonCheckingCreatedOrUpdatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes and processes {@link PersonCheckingDeletedEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The person checking deleted event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "PersonCheckingDeletedEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonCheckingDeletedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes and processes {@link PersonsCheckingCreatedWithCollectiveEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The persons checking created with collective event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "PersonsCheckingCreatedWithCollectiveEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonsCheckingCreatedWithCollectiveEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    /**
     * Consumes and processes {@link PersonsCheckingCreatedEvent} messages from Kafka.
     * Acknowledges the message upon successful processing.
     *
     * @param event The persons checking created event to process.
     * @param ack   The acknowledgment to confirm message processing.
     */
    @KafkaListener(topics = "PersonsCheckingCreatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonsCheckingCreatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
}