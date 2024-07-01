package com.example.employepoc;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.handlers.CheckingCommandHandlers;
import com.example.employepoc.command.handlers.EmployeeCommandHandlers;
import com.example.employepoc.query.handlers.CheckingQueryHandler;
import com.example.employepoc.query.handlers.EmployeeQueryHandler;
import com.example.employepoc.query.queries.*;
import com.hydatis.cqrsref.infrastructure.CommandDispatcher;
import com.hydatis.cqrsref.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableKafka
@EnableScheduling
@ComponentScan({"com.example.employepoc.command.handlers"})
@ComponentScan({"com.example.employepoc.command.events"})

@ComponentScan({"com.example.employepoc.query.rest.repository"})
@ComponentScan({"com.example.employepoc.query.handlers"})
@ComponentScan({"com.example.employepoc.command.infrastructure"})
@ComponentScan({"com.hydatis.cqrsref.infrastructure"})
@ComponentScan({"com.hydatis.cqrsref.handlers"})
public class EmployePocApplication {
    private final EmployeeQueryHandler employeeQueryHandler;
    private final EmployeeCommandHandlers employeeCommandHandlers;
    private final CommandDispatcher commandDispatcher;
    private final QueryDispatcher queryDispatcher;
    private final CheckingCommandHandlers checkingCommandHandlers;

    private final CheckingQueryHandler checkingQueryHandler;
    @Autowired
    public EmployePocApplication(CheckingQueryHandler checkingQueryHandler,CheckingCommandHandlers checkingCommandHandlers,EmployeeQueryHandler employeeQueryHandler, EmployeeCommandHandlers employeeCommandHandlers, CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.employeeQueryHandler = employeeQueryHandler;
        this.employeeCommandHandlers = employeeCommandHandlers;
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
        this.checkingCommandHandlers = checkingCommandHandlers;
        this.checkingQueryHandler = checkingQueryHandler;
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(CreateEmployeeCommand.class, employeeCommandHandlers::handle);
        commandDispatcher.registerHandler(UpdateEmployeeCommand.class, employeeCommandHandlers::handle);
        commandDispatcher.registerHandler(DeleteEmployeeCommand.class, employeeCommandHandlers::handle);
        commandDispatcher.registerHandler(CreateCheckingCommand.class,checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(CreateOrUpdatePersonCheckingCommand.class,checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(DeletePersonCheckingCommand.class,checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(CreatePersonsCheckingWithCollectiveCommand.class,checkingCommandHandlers::handle);
        commandDispatcher.registerHandler(CreatePersonsCheckingCommand.class,checkingCommandHandlers::handle);


        queryDispatcher.registerHandler(FindAllEmployeesQuery.class, employeeQueryHandler::handle);
        queryDispatcher.registerHandler(FindEmployeeByIdQuery.class, employeeQueryHandler::handle);

        queryDispatcher.registerHandler(GetCollectiveCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetDayCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetPersonCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetPersonsCheckingsQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetUserCheckingsByDatesAndPersonsMapQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetUserCheckingsByPersonsAndDatesMapQuery.class, checkingQueryHandler::handle);
        queryDispatcher.registerHandler(GetUserCheckingsQuery.class, checkingQueryHandler::handle);


    }


    public static void main(String[] args) {
        SpringApplication.run(EmployePocApplication.class, args);
    }

}
