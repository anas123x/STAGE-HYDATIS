package com.example.employepoc.query.events;

import com.example.employepoc.command.events.EmployeeCreatedEvent;
import com.example.employepoc.command.events.EmployeeDeletedEvent;
import com.example.employepoc.command.events.EmployeeUpdatedEvent;
import com.example.employepoc.query.rest.dto.Employee;
import com.example.employepoc.query.rest.repository.EmployeeQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeEventHandler implements EmployeeEventHandlerInterface {
    private final EmployeeQueryRepository employeeQueryRepository;

    @Override
    public void on(EmployeeCreatedEvent event) {
        System.out.println("Employee created event received"+event.toString());
    Employee e = new Employee().builder()
            .id(event.getEmployee().getId())
            .name(event.getEmployee().getName())
            .position(event.getEmployee().getPosition())
            .build();
    employeeQueryRepository.save(e);
    }

    @Override
    public void on(EmployeeUpdatedEvent event) {
        Employee e = new Employee().builder()
                .id(event.getEmployee().getId())
                .name(event.getEmployee().getName())
                .position(event.getEmployee().getPosition())
                .build();
        employeeQueryRepository.save(e);

    }

    @Override
    public void on(EmployeeDeletedEvent event) {
        Employee e = new Employee().builder()
                .id(event.getEmployee().getId())
                .name(event.getEmployee().getName())
                .position(event.getEmployee().getPosition())
                .deleted(true)
                .build();

        employeeQueryRepository.save(e);

    }
}
