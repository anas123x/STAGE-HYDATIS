package com.example.employepoc.command.rest.response;

import com.example.employepoc.command.rest.dto.Employee;
import lombok.Data;

@Data
public class EmployeeResponse {
    private String message;
    private Employee employee;

    public EmployeeResponse(String message, Employee employee) {
        this.message = message;
        this.employee = employee;
    }

    public EmployeeResponse(String message) {
        this.message = message;
    }
}
