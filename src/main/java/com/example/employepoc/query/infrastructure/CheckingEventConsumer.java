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

@Service
public class CheckingEventConsumer implements  CheckingEventConsumerInterface{
    private final CheckingEventHandler handler;
    @Autowired
    public CheckingEventConsumer(CheckingEventHandler handler) {
        this.handler = handler;
    }
    private static final Logger logger = LoggerFactory.getLogger(CheckingEventHandler.class);


    @KafkaListener(topics = "CreateCheckingEvent", groupId = "employee-consumer")
    @Override
    public void consume(CreateCheckingEvent event, Acknowledgment ack) {
        System.out.println("Checking created event received"+event.toString());

        this.handler.on(event);
        ack.acknowledge();
    }
    @KafkaListener(topics = "PersonCheckingCreatedOrUpdatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonCheckingCreatedOrUpdatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
    @KafkaListener(topics = "PersonCheckingDeletedEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonCheckingDeletedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
    @KafkaListener(topics = "PersonsCheckingCreatedWithCollectiveEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonsCheckingCreatedWithCollectiveEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
    @KafkaListener(topics = "PersonsCheckingCreatedEvent", groupId = "employee-consumer")
    @Override
    public void consume(PersonsCheckingCreatedEvent event, Acknowledgment ack) {
        this.handler.on(event);
        ack.acknowledge();
    }
}
