package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.*;

public interface CheckingCommandHandlersInterface {
    void handle(CreateCheckingCommand createCheckingCommand);
    void handle(CreateOrUpdatePersonCheckingCommand createOrUpdatePersonCheckingCommand);
    void handle(DeletePersonCheckingCommand deletePersonCheckingCommand);
    void handle (CreatePersonsCheckingCommand createPersonsCheckingCommand);
    void handle(CreatePersonsCheckingWithCollectiveCommand createPersonsCheckingWithCollectiveCommand);
}
