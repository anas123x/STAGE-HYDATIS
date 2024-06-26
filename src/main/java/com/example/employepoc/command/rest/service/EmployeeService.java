package com.example.employepoc.command.rest.service;

import com.example.employepoc.command.rest.dto.Employee;
import com.example.employepoc.command.rest.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }
    public Employee updateEmployee(String id, Employee employeeDetails) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        if(optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setName(employeeDetails.getName());
            employee.setPosition(employeeDetails.getPosition());
            return employeeRepository.save(employee);
        } else {
            return null;
        }
    }
    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }
}