package com.example.employepoc.query.events;

import com.example.employepoc.command.events.*;

public interface CheckingEvenHandlerInterface {
    void on(CreateCheckingEvent event);
    void on(PersonCheckingCreatedOrUpdatedEvent event);
    void on(PersonCheckingDeletedEvent event);
    void on(PersonsCheckingCreatedWithCollectiveEvent event);
    void on(PersonsCheckingCreatedEvent event);


}
