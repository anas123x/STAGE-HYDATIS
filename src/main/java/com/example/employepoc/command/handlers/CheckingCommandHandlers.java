package com.example.employepoc.command.handlers;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.domain.CheckingAggregate;
import com.example.employepoc.command.exceptions.CheckingNotFoundException;
import com.example.employepoc.command.exceptions.PersonNotFoundException;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.dto.Person;
import com.example.employepoc.command.rest.repository.CheckingCommandRepository;
import com.example.employepoc.command.rest.repository.PersonCommandRepository;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service layer responsible for handling commands related to checking operations.
 * Implements the {@link CheckingCommandHandlersInterface} to process various checking-related commands.
 */
@Slf4j
@Service
@ComponentScan("com.example.employepoc.command.rest.repository")
@ComponentScan("com.example.employepoc.command.infrastructure")
@AllArgsConstructor
public class CheckingCommandHandlers implements CheckingCommandHandlersInterface {
    private final EventSourcingHandler<CheckingAggregate> eventSourceHandler;
    private final CheckingCommandRepository checkingCommandRepository;
    private final PersonCommandRepository personCommandRepository;


    /**
     * Handles the creation of a new checking record.
     * Extracts information from the command, creates a new checking record, and persists the event.
     * @param createCheckingCommand Command containing the details for creating a new checking record.
     */
    @Override
    public void handle(CreateCheckingCommand createCheckingCommand) {
        // Extract the necessary information from the command object
        LocalDateTime localDateTime = createCheckingCommand.getChecking().getActualTime();
        String personId = createCheckingCommand.getPersonId();
        Checking.CheckingDirection cd = createCheckingCommand.getChecking().getDirection();
        Checking.CheckingSource s = createCheckingCommand.getChecking().getActualSource();

        ArrayList<Checking> others = createCheckingCommand.getOthers();

            try{
                // Fetch the person from the database
                Person person = personCommandRepository.findById(personId)
                        .orElseThrow(() -> new PersonNotFoundException("Person with ID " + personId + " not found."));
                List<Checking> c = checkingCommandRepository.findByPersonIdAndActualTimeAndDirectionAndActualSource(personId,localDateTime,cd,s);
                System.out.println(c);
                 if (!c.isEmpty()) {
                 throw new RuntimeException("Checking already exists");
                 }
                Checking checking = new Checking();
                checking.setId(UUID.randomUUID().toString());
                checking.setActualTime(localDateTime);
                checking.setPerson(person);
                checking.setDirection(cd);
                checking.setActualSource(s);
                checking.setLogicalTime(localDateTime);
                checking.setMatricule(person.getMatricule());
                checking.setDirectionGenerated(true);
                checking.setIgnoredByCalc(false);
                checking.setUserSetTime(localDateTime);
                checking.setTimesheetId(1001L);
                checking.setUsed(false);
                if (person.getUser() != null){
                    checking.setUser(person.getUser());
                }
                System.out.println("Checking: " + checking.toString());
                Checking newChecking =  checkingCommandRepository.save(checking);
                createCheckingCommand.getChecking().setId(newChecking.getId());
                CheckingAggregate checkingAggregate = new CheckingAggregate(createCheckingCommand);

                // Save the new CheckingAggregate using the eventSourceHandler
                eventSourceHandler.save(checkingAggregate);
            }catch (PersonNotFoundException e){
                throw new PersonNotFoundException("Error creating checking: " + e.getMessage());
            }
            catch (Exception e){
                System.out.println(e);
                throw new RuntimeException("Error creating checking: " + e.getMessage());
            }

    }
    /**
     * Handles the creation or update  of a new checking record.
     * Extracts information from the command, creates a new checking record, and persists the event.
     * @param createOrUpdatePersonCheckingCommand  containing the details for creating or updating a new checking record.
     */
    @Override
    public void handle(CreateOrUpdatePersonCheckingCommand createOrUpdatePersonCheckingCommand) {
        // Extract the necessary information from the command object
        String id = createOrUpdatePersonCheckingCommand.getCheckingId();
        String personId = createOrUpdatePersonCheckingCommand.getPersonId();
        LocalDateTime date = createOrUpdatePersonCheckingCommand.getDate().toDateTimeAtStartOfDay().toLocalDateTime();
        String threeDaysTime = createOrUpdatePersonCheckingCommand.getThreeDaysTime();

        try {
            // Fetch the person from the database
            Person person = personCommandRepository.findById(personId)
                    .orElseThrow(() -> new PersonNotFoundException("Person with ID " + personId + " not found."));

            Checking checking;

            if (id != null) {
                // Update existing checking if ID is provided
                checking = checkingCommandRepository.findById(id)
                        .orElseThrow(() -> new CheckingNotFoundException("Checking with ID " + id + " not found."));
            } else {
                // Create new checking instance
                checking = new Checking();
                checking.setId(UUID.randomUUID().toString());
            }

            // Set common properties
            checking.setActualTime(date);
            checking.setPerson(person);
            checking.setDirection(Checking.CheckingDirection.IN); // Example: Set direction as IN
            checking.setActualSource(Checking.CheckingSource.USER); // Example: Set source as USER
            checking.setLogicalTime(date);
            checking.setMatricule(person.getMatricule()); // Example: Set matricule from person
            checking.setDirectionGenerated(true); // Example: Set direction generated flag
            checking.setIgnoredByCalc(false); // Example: Set ignored by calculation flag
            checking.setUserSetTime(date);
            checking.setTimesheetId(1001L); // Example: Set timesheet ID
            checking.setUsed(false); // Example: Set used flag

            // Set custom threeDaysTime value
            Map<String, String> dataMap = new HashMap<>();
            dataMap.put("threeDaysTime", threeDaysTime);
            checking.setData(dataMap);
            // Save checking to database
             checkingCommandRepository.save(checking);
            CheckingAggregate checkingAggregate = new CheckingAggregate(createOrUpdatePersonCheckingCommand);

            // Save the new CheckingAggregate using the eventSourceHandler
            eventSourceHandler.save(checkingAggregate);
        }
        catch (PersonNotFoundException e) {
            throw new PersonNotFoundException("Error creating or updating checkingaa: " + e.getMessage());
        }
        catch (CheckingNotFoundException e) {
            throw new CheckingNotFoundException("Error creating or updating checkingxx: " + e.getMessage());
        }

        catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("Error creating or updating checkingff: " + e.getMessage());
        }

        // Create a new CheckingAggregate from the new Checking


    }
    /**
     * Handles the deletion of a person checking record.
     * @param deletePersonCheckingCommand Command containing the details for deleting a person checking record.
     */
    @Override
    public void handle(DeletePersonCheckingCommand deletePersonCheckingCommand) {
        Checking checking = deletePersonCheckingCommand.getChecking();
        boolean duplicate = deletePersonCheckingCommand.isDuplicate();

            try  {
                personCommandRepository.findById(checking.getPerson().getId())
                        .orElseThrow(() -> new PersonNotFoundException("Person with ID " + checking.getPerson().getId() + " not found."));

                List<Checking> checkings = checkingCommandRepository.findByPersonIdAndActualTimeAndDirectionAndActualSource(
                        checking.getPerson().getId(),
                        checking.getActualTime(),
                        checking.getDirection(),
                        checking.getActualSource()
                );
                if (checkings.isEmpty()){
                    throw new RuntimeException("no checkings not found");
                }
                if (checkings.size() > 1 && duplicate) {

                    checkings.stream().map(
                            c -> {
                                c.setDeleted(true);
                                return c;
                            }
                    ).forEach(checkingCommandRepository::save);

                } else {
                    checkings.get(0).setDeleted(true);
                    checkingCommandRepository.save(checkings.get(0));
                }
                CheckingAggregate checkingAggregate = new CheckingAggregate(deletePersonCheckingCommand);
                eventSourceHandler.save(checkingAggregate);

            }
            catch (PersonNotFoundException e){
                throw new PersonNotFoundException("Error deleting checking: " + e.getMessage());
            }
            catch (
                    Exception e){
                throw new RuntimeException("Error deleting checking: " + e.getMessage());
            }



    }
    /**
     * Handles the creation of checking records for multiple persons.
     * @param createPersonsCheckingCommand Command containing the details for creating checking records for multiple persons.
     */
    @Override
    public void handle(CreatePersonsCheckingCommand createPersonsCheckingCommand) {
        // Extract the necessary information from the command object
      List<String> personIds = createPersonsCheckingCommand.getPersonIds();
        LocalDate date = createPersonsCheckingCommand.getDate();
        String threeDaysTime = createPersonsCheckingCommand.getThreeDaysTime();
        try {
            List<Checking> checkings = new ArrayList<>();
            for (String personId : personIds) {
                try {
                    Person person = personCommandRepository.findById(personId)
                            .orElseThrow(() -> new PersonNotFoundException("Person with ID " + personId + " not found."));

                    Checking checking = new Checking();
                    checking.setId(UUID.randomUUID().toString());
                    checking.setActualTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
                    checking.setPerson(person);
                    checking.setDirection(Checking.CheckingDirection.IN);
                    checking.setActualSource(Checking.CheckingSource.USER);
                    checking.setLogicalTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
                    checking.setMatricule(person.getMatricule());
                    checking.setDirectionGenerated(true);
                    checking.setIgnoredByCalc(false);
                    checking.setUserSetTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
                    checking.setTimesheetId(1001L);
                    checking.setUsed(false);

                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("threeDaysTime", threeDaysTime);
                    checking.setData(dataMap);

                    checkings.add(checkingCommandRepository.save(checking));
                }catch (PersonNotFoundException e){
                    throw new PersonNotFoundException("Error creating checking records: " + e.getMessage());
                }

                catch (Exception e) {
                    throw new RuntimeException("Error creating checkings for person ID " + personId + ": " + e.getMessage());
                }
            }

            createPersonsCheckingCommand.setCheckings(checkings);
            // Create a new CheckingAggregate from the new Checking
            CheckingAggregate checkingAggregate = new CheckingAggregate(createPersonsCheckingCommand);

            // Save the new CheckingAggregate using the eventSourceHandler
            eventSourceHandler.save(checkingAggregate);
        }
        catch (PersonNotFoundException e) {
            log.error("Error creating checking records: " + e.getMessage());
            throw e;
        }



    }
    /**
     * Handles the creation of checking records for multiple persons with collective information.
     * @param createPersonsCheckingWithCollectiveCommand Command containing the details for creating checking records for multiple persons with additional collective information.
     */
     @Override
    public void handle(CreatePersonsCheckingWithCollectiveCommand createPersonsCheckingWithCollectiveCommand) {
        System.out.println("mesg handler first");

        // Extract the necessary information from the command object
        List<String> personIds = createPersonsCheckingWithCollectiveCommand.getPersonIds();
        LocalDate date = createPersonsCheckingWithCollectiveCommand.getDate();
        String threeDaysTime = createPersonsCheckingWithCollectiveCommand.getThreeDaysTime();
        boolean collective = createPersonsCheckingWithCollectiveCommand.isCollective();
        try {
            List<Checking> checkings = new ArrayList<>();

            for (String personId : personIds) {

                    Person person = personCommandRepository.findById(personId)
                            .orElseThrow(() -> new PersonNotFoundException("Person with ID " + personId + " not found."));

                    Checking checking = new Checking();
                    checking.setId(UUID.randomUUID().toString());
                    checking.setActualTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
                    checking.setPerson(person);
                    checking.setDirection(Checking.CheckingDirection.IN);
                    checking.setActualSource(Checking.CheckingSource.USER);
                    checking.setLogicalTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
                    checking.setMatricule(person.getMatricule());
                    checking.setDirectionGenerated(true);
                    checking.setIgnoredByCalc(false);
                    checking.setUserSetTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
                    checking.setTimesheetId(1001L);
                    checking.setUsed(false);
                    Map<String, String> dataMap = new HashMap<>();
                    dataMap.put("threeDaysTime", threeDaysTime);
                    dataMap.put("collective",collective ? "true" : "false");
                    checking.setData(dataMap);
                    checkings.add(checking);


            }
            createPersonsCheckingWithCollectiveCommand.setCheckings(checkings);

            checkingCommandRepository.saveAll(checkings);
            // Create a new CheckingAggregate from the new Checking
            CheckingAggregate checkingAggregate = new CheckingAggregate(createPersonsCheckingWithCollectiveCommand);

            // Save the new CheckingAggregate using the eventSourceHandler
            eventSourceHandler.save(checkingAggregate);
        }
        catch (PersonNotFoundException e) {
            log.error("Error creating checking records: " + e.getMessage());
            throw e;
        }
        catch (Exception e)
        {
            log.error("Error creating checking records: " + e.getMessage());
            throw new RuntimeException("Error creating checking records: " + e.getMessage());
        }



    }
}
