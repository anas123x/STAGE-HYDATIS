package com.example.employepoc.command.rest.controllers;

import com.example.employepoc.command.events.EmployeeEvents;
import com.example.employepoc.command.rest.dto.Employee;
import com.example.employepoc.command.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final KafkaTemplate<String, EmployeeEvents> kafkaTemplate;


    @Autowired
    public EmployeeController(EmployeeService employeeService, KafkaTemplate<String, EmployeeEvents> kafkaTemplate) {
        this.employeeService = employeeService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping
    public Employee saveEmployee(@RequestBody Employee employee) {
        // Save the Employee object to the database first
        Employee savedEmployee = employeeService.saveEmployee(employee);
        // Then create the EmployeeEvents object and send it to the Kafka topic
        EmployeeEvents employeeEvents = new EmployeeEvents("EmployeeCreated", savedEmployee);
        kafkaTemplate.send("employee-events", employeeEvents);
        return savedEmployee;
    }
    @PutMapping("/{id}")
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
    }
}