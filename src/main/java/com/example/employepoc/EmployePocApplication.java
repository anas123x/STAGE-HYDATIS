package com.example.employepoc;

import com.example.employepoc.command.commands.CreateEmployeeCommand;
import com.example.employepoc.command.commands.DeleteEmployeeCommand;
import com.example.employepoc.command.commands.UpdateEmployeeCommand;
import com.example.employepoc.command.handlers.EmployeeCommandHandlers;
import com.example.employepoc.query.handlers.EmployeeQueryHandler;
import com.example.employepoc.query.queries.FindAllEmployeesQuery;
import com.example.employepoc.query.queries.FindEmployeeByIdQuery;
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

    @Autowired
    public EmployePocApplication(EmployeeQueryHandler employeeQueryHandler, EmployeeCommandHandlers employeeCommandHandlers, CommandDispatcher commandDispatcher, QueryDispatcher queryDispatcher) {
        this.employeeQueryHandler = employeeQueryHandler;
        this.employeeCommandHandlers = employeeCommandHandlers;
        this.commandDispatcher = commandDispatcher;
        this.queryDispatcher = queryDispatcher;
    }

    @PostConstruct
    public void registerHandlers() {
        commandDispatcher.registerHandler(CreateEmployeeCommand.class, employeeCommandHandlers::handle);
        commandDispatcher.registerHandler(UpdateEmployeeCommand.class, employeeCommandHandlers::handle);
        commandDispatcher.registerHandler(DeleteEmployeeCommand.class, employeeCommandHandlers::handle);


        queryDispatcher.registerHandler(FindAllEmployeesQuery.class, employeeQueryHandler::handle);
        queryDispatcher.registerHandler(FindEmployeeByIdQuery.class, employeeQueryHandler::handle);

    }


    public static void main(String[] args) {
        SpringApplication.run(EmployePocApplication.class, args);
    }

}
