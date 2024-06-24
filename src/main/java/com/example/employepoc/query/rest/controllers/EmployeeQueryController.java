package com.example.employepoc.query.rest.controllers;

import com.example.employepoc.command.events.EmployeeEvents;
import com.example.employepoc.query.rest.dto.Employee;
import com.example.employepoc.query.rest.repository.EmployeeQueryRepository;
import com.example.employepoc.query.rest.services.EmployeeQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeQueryController {

    private final EmployeeQueryService employeeQueryService;
    private final EmployeeQueryRepository employeeQueryRepository;

    @Autowired
    public EmployeeQueryController(EmployeeQueryService employeeQueryService, EmployeeQueryRepository employeeQueryRepository) {
        this.employeeQueryService = employeeQueryService;
        this.employeeQueryRepository = employeeQueryRepository;
    }

    @KafkaListener(topics = "employee-events", groupId = "employee-consumer")
    public void consumeEmployeeEvents(EmployeeEvents employeeEvent) {
        System.out.println("Consumed type event: " + employeeEvent.getType());

        System.out.println("Consumed employee event: " + employeeEvent.getEmployee());
        Employee employee = new Employee();
        employee.setId(employeeEvent.getEmployee().getId());
        employee.setName(employeeEvent.getEmployee().getName());
        employee.setPosition(employeeEvent.getEmployee().getPosition());
        switch (employeeEvent.getType()) {
            case "EmployeeCreated":
                employeeQueryRepository.save(employee);
                break;
            case "EmployeeUpdated":
                try {
                    System.out.println("idddddd"+employeeEvent.getEmployee().getId());
                    employeeQueryRepository.findById(employeeEvent.getEmployee().getId())
                            .ifPresentOrElse(employee1 -> {
                                employee1.setName(employee.getName());
                                employee1.setPosition(employee.getPosition());
                                employeeQueryRepository.save(employee1);
                            }, () -> {
                                System.out.println("Employee not found");
                            });
                } catch (Exception e) {
                    System.out.println(e);
                }

                break;
            case "EmployeeDeleted":
                employeeQueryRepository.deleteById(employee.getId());
                break;
        }





    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeQueryService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id) {
        return employeeQueryService.getEmployeeById(id);
    }
}