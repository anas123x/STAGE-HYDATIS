package com.example.employepoc.query.rest.controllers;

import com.example.employepoc.query.queries.FindAllEmployeesQuery;
import com.example.employepoc.query.queries.FindEmployeeByIdQuery;
import com.example.employepoc.query.rest.dto.Employee;
import com.example.employepoc.query.rest.response.FindAllEmpoyeesResponse;
import com.example.employepoc.query.rest.response.FindEmployeeByIdResponse;
import com.hydatis.cqrsref.infrastructure.IQueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeQueryController {

@Autowired
    private IQueryDispatcher queryDispatcher;
 @GetMapping
    public ResponseEntity<FindAllEmpoyeesResponse> getAllEmployees() {
        List<Employee> employees = queryDispatcher.send(new FindAllEmployeesQuery());
        var response = FindAllEmpoyeesResponse.builder()
                .employees(employees)
                .message("Employees retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

   /* @GetMapping("/{id}")
    public ResponseEntity<FindEmployeeByIdResponse> getEmployeeById(@PathVariable Long id) {
        Employee employee = queryDispatcher.send(new FindEmployeeByIdQuery(id));
        var response = FindEmployeeByIdResponse.builder()
                .employee(employee)
                .message("Employee retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }*/
/*    @KafkaListener(topics = "employee-events", groupId = "employee-consumer")
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
    }*/
}