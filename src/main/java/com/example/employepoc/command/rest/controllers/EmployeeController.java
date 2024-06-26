package com.example.employepoc.command.rest.controllers;

import com.example.employepoc.command.commands.CreateEmployeeCommand;
import com.example.employepoc.command.commands.DeleteEmployeeCommand;
import com.example.employepoc.command.commands.UpdateEmployeeCommand;
import com.example.employepoc.command.rest.dto.Employee;
import com.hydatis.cqrsref.infrastructure.CommandDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.employepoc.command.rest.response.EmployeeResponse;

import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor

public class EmployeeController {


    private final CommandDispatcher commandDispatcher;




 /*   @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        // Save the Employee object to the database first
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // Then create the EmployeeEvents object and send it to the Kafka topic
        EmployeeEvents employeeEvents = new EmployeeEvents("EmployeeCreated", savedEmployee);
        kafkaTemplate.send("employee-events", employeeEvents);
        return savedEmployee;
    }*/
    @PostMapping
    public ResponseEntity<EmployeeResponse> saveEmployee(@RequestBody Employee employee) {
        CreateEmployeeCommand createEmployeeCommand = new CreateEmployeeCommand();
        var id = UUID.randomUUID().toString();
        createEmployeeCommand.setId(id);
        createEmployeeCommand.setEmployee(employee);
        commandDispatcher.send(createEmployeeCommand);
        return ResponseEntity.ok(new EmployeeResponse("Empoyee created", createEmployeeCommand.getEmployee()));


    }
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> updateEmployee(@PathVariable String id,@RequestBody Employee employee) {
        UpdateEmployeeCommand updatedEmployee = new UpdateEmployeeCommand();
        updatedEmployee.setId(id);
        updatedEmployee.setEmployee(employee);
        updatedEmployee.getEmployee().setId(id);
        commandDispatcher.send(updatedEmployee);
        return ResponseEntity.ok(new EmployeeResponse("Employee updated", updatedEmployee.getEmployee()));}
    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> deleteEmployee(@PathVariable String id,@RequestBody Employee employee) {
        DeleteEmployeeCommand deletedEmployeeCommand = new DeleteEmployeeCommand();
        deletedEmployeeCommand.setId(id);
        deletedEmployeeCommand.setEmployee(employee);
        deletedEmployeeCommand.getEmployee().setId(id);
        deletedEmployeeCommand.getEmployee().setDeleted(true);
        commandDispatcher.send(deletedEmployeeCommand);
        return ResponseEntity.ok(new EmployeeResponse("Employee deleted", deletedEmployeeCommand.getEmployee()));}
    /*@PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
       Employee updatedEmployee = employeeService.updateEmployee(id, employee);
         EmployeeEvents employeeEvents = new EmployeeEvents("EmployeeUpdated", updatedEmployee);
            kafkaTemplate.send("employee-events", employeeEvents);
            return updatedEmployee;
    }
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {

        employeeService.deleteEmployee(id);
        Employee employee = new Employee();
        employee.setId(Long.valueOf(String.valueOf(id)));
        EmployeeEvents employeeEvents = new EmployeeEvents("EmployeeDeleted", employee);
        kafkaTemplate.send("employee-events", employeeEvents);
    }*/
}