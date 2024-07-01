package com.example.employepoc.query.infrastructure;

import com.example.employepoc.command.events.*;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface CheckingEventConsumerInterface {
    void consume(@Payload CreateCheckingEvent event, Acknowledgment ack);
    void consume(@Payload PersonCheckingCreatedOrUpdatedEvent event, Acknowledgment ack);
    void consume(@Payload PersonCheckingDeletedEvent event, Acknowledgment ack);
    void consume(@Payload PersonsCheckingCreatedWithCollectiveEvent event, Acknowledgment ack);
    void consume(@Payload PersonsCheckingCreatedEvent event, Acknowledgment ack);

}
