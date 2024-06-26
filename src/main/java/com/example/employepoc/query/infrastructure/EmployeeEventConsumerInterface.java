package com.example.employepoc.query.infrastructure;

import com.example.employepoc.command.events.EmployeeCreatedEvent;
import com.example.employepoc.command.events.EmployeeDeletedEvent;
import com.example.employepoc.command.events.EmployeeUpdatedEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EmployeeEventConsumerInterface {
    void consume(@Payload EmployeeCreatedEvent event, Acknowledgment ack);

    void consume(@Payload EmployeeUpdatedEvent event, Acknowledgment ack);

    void consume(@Payload EmployeeDeletedEvent event, Acknowledgment ack);
}
