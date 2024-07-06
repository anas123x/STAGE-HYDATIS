package com.example.employepoc.query.events;

import com.example.employepoc.command.events.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import com.example.employepoc.query.rest.repository.CheckingQueryRepository;
import com.example.employepoc.query.rest.repository.PersonQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        checkingEntity.setId(event.getChecking().getId()
        );
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
        try {
            // Fetch the person from the database
            Person person = personQueryRepository.findById(event.getPersonId())
                    .orElseThrow(() -> new RuntimeException("Person with ID " + event.getPersonId() + " not found."));

            // Create or update the checking
            Checking checking;
            if (event.getCheckingId() != null && !event.getCheckingId().isEmpty()) {
                // Attempt to update existing checking
                checking = repository.findById(event.getCheckingId())
                        .orElseThrow(() -> new RuntimeException("Checking with ID " + event.getCheckingId() + " not found."));
            } else {
                // Create a new checking
                checking = new Checking();
                checking.setId(UUID.randomUUID().toString()); // Generate a new ID for the checking
            }

            // Set properties from the event
            checking.setActualTime(event.getDate().toDateTimeAtStartOfDay().toLocalDateTime()); // Assuming conversion is needed
            checking.setPerson(person);
            checking.setLogicalTime(event.getDate().toDateTimeAtStartOfDay().toLocalDateTime());
            checking.setDirectionGenerated(true); // Example: Set direction generated flag
            checking.setIgnoredByCalc(false); // Example: Set ignored by calculation flag
            checking.setTimesheetId(1001L); // Example: Set timesheet ID
            checking.setUsed(false);
            checking.setActualSource(Checking.CheckingSource.USER);
            checking.setDirection(Checking.CheckingDirection.IN);
            checking.setMatricule(person.getMatricule()); // Example: Set matricule from person
            // Additional properties like direction, source, etc., should be set here based on your application's requirements
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("threeDaysTime", event.getThreeDaysTime());
            checking.setData(dataMap);
            // Save the checking
            repository.save(checking);
        } catch (Exception e) {
            System.out.println("Error processing PersonCheckingCreatedOrUpdatedEvent: " + e.getMessage());
            // Handle exception as needed
        }
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
        for (int i = 0; i < event.getPersonIds().size(); i++) {
            Long personId = event.getPersonIds().get(i);
            try {
                // Fetch the person from the database
                Person person = personQueryRepository.findById(personId)
                        .orElseThrow(() -> new RuntimeException("Person with ID " + personId + " not found."));

                // Create a new checking
                Checking checking = new Checking();
                checking.setId(event.getCheckings().get(i).getId());
                checking.setActualTime(event.getDate().toDateTimeAtStartOfDay().toLocalDateTime()); // Assuming conversion is needed
                checking.setLogicalTime(event.getDate().toDateTimeAtStartOfDay().toLocalDateTime());
                checking.setPerson(person);
                checking.setDirectionGenerated(true); // Example: Set direction generated flag
                checking.setIgnoredByCalc(false); // Example: Set ignored by calculation flag
                checking.setTimesheetId(1001L); // Example: Set timesheet ID
                checking.setUsed(false);
                checking.setActualSource(Checking.CheckingSource.USER); // Assuming USER as source
                checking.setDirection(Checking.CheckingDirection.IN); // Assuming IN as direction
                checking.setMatricule(person.getMatricule()); // Set matricule from person

                // Additional properties like direction, source, etc., should be set here based on your application's requirements
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("threeDaysTime", event.getThreeDaysTime());
                dataMap.put("collective",event.isCollective() ? "true" : "false");
                checking.setData(dataMap);

                // Save the checking
                repository.save(checking);
            } catch (Exception e) {
                System.out.println("Error processing PersonsCheckingCreatedWithCollectiveEvent for person ID " + personId + ": " + e.getMessage());
                // Handle exception as needed
            }
        }
    }

    /**
     * Placeholder for processing events related to the creation of checkings for multiple persons.
     * Currently not implemented.
     *
     * @param event The event containing the data of the created checkings for multiple persons.
     */
    @Override
    public void on(PersonsCheckingCreatedEvent event) {
        for (int i = 0; i < event.getPersonIds().size(); i++) {
            Long personId = event.getPersonIds().get(i);
            try {
                // Fetch the person from the database
                Person person = personQueryRepository.findById(personId)
                        .orElseThrow(() -> new RuntimeException("Person with ID " + personId + " not found."));

                // Create a new checking
                Checking checking = new Checking();
                checking.setId(event.getCheckings().get(i).getId());
                checking.setActualTime(event.getDate().toDateTimeAtStartOfDay().toLocalDateTime()); // Assuming conversion is needed
                checking.setLogicalTime(event.getDate().toDateTimeAtStartOfDay().toLocalDateTime());
                checking.setPerson(person);
                checking.setDirectionGenerated(true); // Example: Set direction generated flag
                checking.setIgnoredByCalc(false); // Example: Set ignored by calculation flag
                checking.setTimesheetId(1001L); // Example: Set timesheet ID
                checking.setUsed(false);
                checking.setActualSource(Checking.CheckingSource.USER); // Assuming USER as source
                checking.setDirection(Checking.CheckingDirection.IN); // Assuming IN as direction
                checking.setMatricule(person.getMatricule()); // Set matricule from person

                // Additional properties like direction, source, etc., should be set here based on your application's requirements
                Map<String, String> dataMap = new HashMap<>();
                dataMap.put("threeDaysTime", event.getThreeDaysTime());
                checking.setData(dataMap);

                // Save the checking
                repository.save(checking);
            } catch (Exception e) {
                System.out.println("Error processing PersonsCheckingCreatedEvent for person ID " + personId + ": " + e.getMessage());
                // Handle exception as needed
            }
        }
    }
}