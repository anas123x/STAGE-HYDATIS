package com.example.employepoc.query.infrastructure;

import com.example.employepoc.command.events.EmployeeCreatedEvent;
import com.example.employepoc.command.events.EmployeeDeletedEvent;
import com.example.employepoc.command.events.EmployeeUpdatedEvent;
import com.example.employepoc.query.events.EmployeeEventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service

public class EmployeeEventConsumer implements EmployeeEventConsumerInterface {
    private final EmployeeEventHandler handler;

    @Autowired
    public EmployeeEventConsumer(EmployeeEventHandler handler) {
        this.handler = handler;
    }

    @KafkaListener(topics = "EmployeeCreatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(EmployeeCreatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "EmployeeUpdatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(EmployeeUpdatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }

    @KafkaListener(topics = "EmployeeDeletedEvent", groupId = "employee-consumer")
    @Override
    public void consume(EmployeeDeletedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
}
