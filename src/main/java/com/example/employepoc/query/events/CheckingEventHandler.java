package com.example.employepoc.query.events;

import com.example.employepoc.command.events.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import com.example.employepoc.query.rest.repository.CheckingQueryRepository;
import com.example.employepoc.query.rest.repository.PersonQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service for handling events related to checkings.
 * This service listens to various checking-related events and processes them accordingly,
 * such as creating, updating, or deleting checkings in the system.
 */
@Service
@RequiredArgsConstructor
public class CheckingEventHandler implements CheckingEvenHandlerInterface {

    private final CheckingQueryRepository repository;
    private final PersonQueryRepository personQueryRepository;

    /**
     * Processes the event of a checking being created.
     * Converts the event data into a Checking entity and saves it to the database.
     * If the person associated with the checking is found, it links the checking to that person.
     *
     * @param event The event containing the data of the created checking.
     */
    @Override
    public void on(CreateCheckingEvent event) {
        System.out.println("Checking created event received" + event.toString());

        // Convert the event's checking to your Checking entity
        Checking checkingEntity = new Checking();
        checkingEntity.setId(UUID.randomUUID().toString());
        checkingEntity.setActualTime(event.getChecking().getActualTime());
        checkingEntity.setLogicalTime(event.getChecking().getLogicalTime());
        checkingEntity.setData(event.getChecking().getData());
        checkingEntity.setDirectionGenerated(true);
        checkingEntity.setIgnoredByCalc(false);
        checkingEntity.setUserSetTime(event.getChecking().getActualTime());
        checkingEntity.setActualSource(com.example.employepoc.query.rest.dto.Checking.CheckingSource.valueOf(event.getChecking().getActualSource().name()));
        checkingEntity.setDirection(com.example.employepoc.query.rest.dto.Checking.CheckingDirection.valueOf(event.getChecking().getDirection().name()));
        checkingEntity.setUsed(false);
        checkingEntity.setTimesheetId(1001L);
        try {
            Person p = personQueryRepository.findById(event.getPersonId()).get();
            checkingEntity.setPerson(p);
            checkingEntity.setMatricule(p.getMatricule());
            repository.save(checkingEntity);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * Placeholder for processing events related to the creation or update of a person's checking.
     * Currently not implemented.
     *
     * @param event The event containing the data of the created or updated checking.
     */
    @Override
    public void on(PersonCheckingCreatedOrUpdatedEvent event) {
        // Implementation goes here
    }

    /**
     * Processes the event of a checking being deleted.
     * Marks the checking as deleted in the database. If the event is marked as duplicate,
     * it checks for duplicate checkings and marks them as deleted.
     *
     * @param event The event containing the data of the deleted checking.
     */
    @Override
    public void on(PersonCheckingDeletedEvent event) {
        System.out.println("Checking deleted event received" + event.toString());
        boolean duplicate = event.isDuplicate();
        Checking checking = new Checking();
        checking.setActualTime(event.getChecking().getActualTime());
        checking.setActualSource(com.example.employepoc.query.rest.dto.Checking.CheckingSource.valueOf(event.getChecking().getActualSource().name()));
        checking.setDirection(com.example.employepoc.query.rest.dto.Checking.CheckingDirection.valueOf(event.getChecking().getDirection().name()));
        Person p = personQueryRepository.findById(event.getChecking().getPerson().getId()).get();
        checking.setPerson(p);
        if (duplicate) {
            List<Checking> checkings = repository.findByPersonIdAndActualTimeAndDirectionAndActualSource(
                    checking.getPerson().getId(),
                    checking.getActualTime(),
                    checking.getDirection(),
                    checking.getActualSource()
            );
            if (checkings.size() > 1) {
                for (Checking c : checkings) {
                    c.setDeleted(true);
                    repository.save(c);
                }
            } else {
                checkings.get(0).setDeleted(true);
                repository.save(checkings.get(0));
            }
        } else {
            checking.setDeleted(true);
            repository.save(checking);
        }

    }

    /**
     * Placeholder for processing events related to the creation of checkings for multiple persons collectively.
     * Currently not implemented.
     *
     * @param event The event containing the data of the collectively created checkings.
     */
    @Override
    public void on(PersonsCheckingCreatedWithCollectiveEvent event) {
        // Implementation goes here
    }

    /**
     * Placeholder for processing events related to the creation of checkings for multiple persons.
     * Currently not implemented.
     *
     * @param event The event containing the data of the created checkings for multiple persons.
     */
    @Override
    public void on(PersonsCheckingCreatedEvent event) {
        // Implementation goes here
    }
}