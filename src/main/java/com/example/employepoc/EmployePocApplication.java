package com.example.employepoc;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.handlers.CheckingCommandHandlers;
import com.example.employepoc.query.handlers.CheckingQueryHandler;
import com.example.employepoc.query.queries.*;
import com.hydatis.cqrsref.infrastructure.CommandDispatcher;
import com.hydatis.cqrsref.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

/**
 * The main application class for the Employee Proof of Concept (POC) application.
 * This class is responsible for bootstrapping and configuring the Spring Boot application.
 * It includes configuration for Kafka, scheduling, component scanning, and MongoDB repositories.
 */
@SpringBootApplication
@EnableKafka // Enables Kafka support in the application.
@EnableScheduling // Enables scheduling support.
@ComponentScan({"com.example.employepoc.command.handlers"})
@ComponentScan({"com.example.employepoc.command.events"})
@ComponentScan({"com.example.employepoc"})
@ComponentScan({"com.example.employepoc.query.rest.repository"})
@ComponentScan({"com.example.employepoc.query.handlers"})
@ComponentScan({"com.example.employepoc.command.infrastructure"})
@ComponentScan({"com.hydatis.cqrsref.infrastructure"})
@ComponentScan({"com.hydatis.cqrsref.handlers"})
@EnableMongoRepositories(basePackages = "com.example.employepoc.query.rest.repository") // Configures Spring Data MongoDB to scan for repositories in the specified package.

public class EmployePocApplication {

    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;
    private final CheckingCommandHandlers checkingCommandHandlers;
    private final CheckingQueryHandler checkingQueryHandler;

    /**
     * Constructs the main application class, injecting dependencies required for operation.
     *
     * @param checkingQueryHandler The handler for query operations related to checkings.
     * @param checkingCommandHandlers The handler for command operations related to checkings.
     * @param commandDispatcher The dispatcher for command operations.
     * @param queryDispatcher The dispatcher for query operations.
     */
    @Autowired
    public EmployePocApplication(CheckingQueryHandler checkingQueryHandler,CheckingCommandHandlers checkingCommandHandlers, CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
        this.checkingCommandHandlers = checkingCommandHandlers;
        this.checkingQueryHandler = checkingQueryHandler;
    }

    /**
     * Registers handlers with the command and query dispatchers.
     * This method is called after the bean's properties have been set.
     */
    @PostConstruct
    public void registerHandlers() {
        // Register command handlers
        commandDispatcher.registerHandler(CreateCheckingCommand.class, checkingCommandHandlers::handle) ;
        commandDispatcher.registerHandler(CreateOrUpdatePersonCheckingCommand.class, checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(DeletePersonCheckingCommand.class, checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(CreatePersonsCheckingWithCollectiveCommand.class, checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(CreatePersonsCheckingCommand.class, checkingCommandHandlers::handle);

        // Register query handlers
        queryDispatcher.registerHandler(GetCollectiveCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetDayCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetPersonCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetPersonsCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetUserCheckingsByDatesAndPersonsMapQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetUserCheckingsByPersonsAndDatesMapQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetUserCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetAllCheckingsQuery.class, checkingQueryHandler::handle);

    }

    /**
     * The main method that serves as the entry point for the Spring Boot application.
     *
     * @param args The command line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(EmployePocApplication.class, args);
    }
}