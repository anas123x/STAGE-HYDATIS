package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Employee;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize
public class EmployeeCreatedEvent extends BaseEvent {
    private String identifier;
    private Employee employee;

}
