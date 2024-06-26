package com.example.employepoc.query.events;
import com.example.employepoc.command.events.EmployeeCreatedEvent;
import com.example.employepoc.command.events.EmployeeDeletedEvent;
import com.example.employepoc.command.events.EmployeeUpdatedEvent;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeEventHandlerInterface {
    void on(EmployeeCreatedEvent event);

    void on(EmployeeUpdatedEvent event);

    void on(EmployeeDeletedEvent event);
}
