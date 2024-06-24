package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Employee;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEvents {
    private String type;
    private Employee employee;
}
