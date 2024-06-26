package com.example.employepoc.command.domain;

import com.example.employepoc.command.commands.CreateEmployeeCommand;
import com.example.employepoc.command.commands.DeleteEmployeeCommand;
import com.example.employepoc.command.commands.UpdateEmployeeCommand;
import com.example.employepoc.command.events.EmployeeCreatedEvent;
import com.example.employepoc.command.events.EmployeeDeletedEvent;
import com.example.employepoc.command.events.EmployeeUpdatedEvent;
import com.hydatis.cqrsref.domain.AggregateRoot;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeAggregate extends AggregateRoot {
    private String id;


    public EmployeeAggregate(CreateEmployeeCommand command) {
        raiseEvent(EmployeeCreatedEvent.builder()
                .identifier(command.getId())
                .employee(command.getEmployee())
                .build());
    }

    public void apply(EmployeeUpdatedEvent event) {
        this.id = event.getIdentifier();
    }
    public EmployeeAggregate(UpdateEmployeeCommand command) {
        raiseEvent(EmployeeUpdatedEvent.builder()
                .identifier(command.getId())
                .employee(command.getEmployee())
                .build());
    }

    public void apply(EmployeeDeletedEvent event) {
        this.id = event.getIdentifier();
    }
    public EmployeeAggregate(DeleteEmployeeCommand command) {
        raiseEvent(EmployeeDeletedEvent.builder()
                .identifier(command.getId())
                .employee(command.getEmployee())
                .build());
    }

    public void apply(EmployeeCreatedEvent event) {
        this.id = event.getIdentifier();
    }


}
