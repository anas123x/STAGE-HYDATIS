package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.CreateEmployeeCommand;
import com.example.employepoc.command.commands.DeleteEmployeeCommand;
import com.example.employepoc.command.commands.UpdateEmployeeCommand;
import com.example.employepoc.command.domain.EmployeeAggregate;
import com.example.employepoc.command.rest.dto.Employee;
import com.example.employepoc.command.rest.repository.EmployeeRepository;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@ComponentScan("com.example.employepoc.command.rest.repository")
@ComponentScan("com.example.employepoc.infrastructure")
public class EmployeeCommandHandlers implements EmployeeCommandHandlersInterface{
    private final EventSourcingHandler<EmployeeAggregate> eventSourceHandler;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeCommandHandlers(EventSourcingHandler<EmployeeAggregate> eventSourceHandler, EmployeeRepository employeeRepository) {
        this.eventSourceHandler = eventSourceHandler;
        this.employeeRepository = employeeRepository;
    }


    @Override
    public void handle(CreateEmployeeCommand createEmployeeCommand) {
        Employee newEmployee = Employee.builder()
                .id((UUID.randomUUID().toString()))
                .name(createEmployeeCommand.getEmployee().getName())
                .position(createEmployeeCommand.getEmployee().getPosition())
                .build();
        System.out.println("Employee to save: " + newEmployee.toString());
        employeeRepository.save(newEmployee);
        createEmployeeCommand.getEmployee().setId(newEmployee.getId());
        EmployeeAggregate employeeAggregate = new EmployeeAggregate(createEmployeeCommand);
        eventSourceHandler.save(employeeAggregate);
    }

    @Override
    public void handle(UpdateEmployeeCommand updateEmployeeCommand) {
        Employee employeeToUpdate = employeeRepository.findById(updateEmployeeCommand.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeToUpdate.setName(updateEmployeeCommand.getEmployee().getName());
        employeeToUpdate.setPosition(updateEmployeeCommand.getEmployee().getPosition());
        employeeToUpdate.setDeleted(updateEmployeeCommand.getEmployee().isDeleted());
        employeeRepository.save(employeeToUpdate);
        EmployeeAggregate employeeAggregate = new EmployeeAggregate(updateEmployeeCommand);
        eventSourceHandler.save(employeeAggregate);
    }
    @Override
    public void handle(DeleteEmployeeCommand deleteEmployeeCommand) {
        Employee employeeToDelete = employeeRepository.findById(deleteEmployeeCommand.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
       employeeToDelete.setDeleted(true);
        employeeRepository.save(employeeToDelete);
        EmployeeAggregate employeeAggregate = new EmployeeAggregate(deleteEmployeeCommand);
        eventSourceHandler.save(employeeAggregate);
    }
}
