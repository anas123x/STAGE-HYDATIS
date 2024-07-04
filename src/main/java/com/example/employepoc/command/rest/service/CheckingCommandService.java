package com.example.employepoc.command.rest.service;

import com.example.employepoc.command.exceptions.CheckingCreatedException;
import com.example.employepoc.command.exceptions.CheckingDeletedException;
import com.example.employepoc.command.exceptions.PersonNotFoundException;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.dto.Person;
import com.example.employepoc.command.rest.repository.CheckingCommandRepository;
import com.example.employepoc.command.rest.repository.PersonCommandRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for handling checking-related commands.
 */
@Service
public class CheckingCommandService implements ICheckingCommandService{

    @Autowired
    private CheckingCommandRepository checkingCommandRepository;

    @Autowired
    private PersonCommandRepository personCommandRepository;

    /**
     * Creates a new checking record in the database.
     *
     * @param localDateTime The actual time of the checking.
     * @param personId The ID of the person associated with the checking.
     * @param cd The direction of the checking (IN/OUT).
     * @param s The source of the checking.
     * @param others Additional checkings related to the current operation.
     * @return The created Checking object, or null if the person is not found.
     */
    @Override
    public Checking createChecking(LocalDateTime localDateTime, Long personId, Checking.CheckingDirection cd, Checking.CheckingSource s, ArrayList<Checking> others) {
        try{
            System.out.println("personID" + personId + "localDateTime" + localDateTime + "Checking" + cd + "sourec " + s + "other" + others);
            // Fetch the person from the database
            Person person = personCommandRepository.findById(personId)
                    .orElseThrow(() -> new PersonNotFoundException("Person with ID " + personId + " not found."));
            ;
            if (person == null) {
                System.out.println("person is null");
                return null;
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
            System.out.println("Checking: " + checking.toString());
            return checkingCommandRepository.save(checking);
        }catch (PersonNotFoundException e){
            throw new PersonNotFoundException("Error creating checking: " + e.getMessage());
        }
        catch (Exception e){
            throw new CheckingCreatedException("Error creating checking: " + e.getMessage());
        }
    }

    /**
     * Deletes a person's checking record, handling duplicates if specified.
     *
     * @param checking The checking record to delete.
     * @param duplicate Whether to handle duplicate records.
     */
    public void deletePersonChecking(Checking checking, boolean duplicate) {
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
                    throw new CheckingDeletedException("no checkings not found");
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

        }
      catch (PersonNotFoundException e){
          throw new PersonNotFoundException("Error deleting checking: " + e.getMessage());
      }
      catch (
                Exception e){
            throw new CheckingDeletedException("Error deleting checking: " + e.getMessage());
        }

    }

    /**
     * Creates or updates a person's checking record based on the provided ID.
     *
     * @param id The ID of the checking to create or update.
     * @param personId The ID of the person associated with the checking.
     * @param date The logical date of the checking.
     * @param threeDaysTime Additional time information (not used in current implementation).
     * @return The updated or newly created Checking object, or null if not found.
     */
    @Override
    public Checking createOrUpdatePersonChecking(String id, Long personId, LocalDate date, String threeDaysTime) {
        // Fetch the Checking from the database
        Checking checking = checkingCommandRepository.findById(id).orElse(null);
        if (checking == null) {
            // If the Checking does not exist, create a new one
            checking = new Checking();
            checking.setId(UUID.randomUUID().toString()); // Generate a new ID
            checking.setActualTime(date.toDateTimeAtStartOfDay().toLocalDateTime()); // Set the actual time based on the logical date
            // Fetch the person associated with the ID
            Person person = personCommandRepository.findById(personId).orElse(null);
            if (person == null) {
                System.out.println("Person not found");
                return null; // Return null if the person is not found
            }
            checking.setPerson(person); // Set the person for the checking
        } else {
            // If the Checking exists, update its logical time
            checking.setLogicalTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
        }
        // Set other necessary fields for the checking
        checking.setUsed(false); // Example of setting a default value
        checking.setIgnoredByCalc(false); // Example of setting a default value

        // Save the Checking to the database
        return checkingCommandRepository.save(checking);
    }

    /**
     * Creates checkings for multiple persons on a given date.
     *
     * @param personIds The IDs of the persons for whom to create checkings.
     * @param date The date for the checkings.
     * @param threeDaysTime Additional time information (not used in current implementation).
     * @return A list of created Checking objects.
     */
    @Override
    public List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime) {
        List<Checking> createdCheckings = new ArrayList<>();
        for (Long personId : personIds) {
            // For each person ID, create a new checking
            Checking newChecking = new Checking();
            newChecking.setId(UUID.randomUUID().toString()); // Generate a new ID
            newChecking.setActualTime(date.toDateTimeAtStartOfDay().toLocalDateTime()); // Set the actual time based on the date
            Person person = personCommandRepository.findById(personId).orElse(null);
            if (person != null) {
                newChecking.setPerson(person); // Set the person for the checking
                createdCheckings.add(checkingCommandRepository.save(newChecking)); // Save the checking and add it to the list
            }
        }
        return createdCheckings; // Return the list of created checkings
    }
    /**
     * Creates checkings for multiple persons, with an option for collective processing.
     *
     * @param personIds A list of person IDs.
     * @param date The date of the checkings.
     * @param threeDaysTime A string representation of a time span related to the checkings.
     * @param collective A flag indicating if the checkings should be processed as a collective action.
     * @return A list of created Checking instances.
     */
    @Override
    public List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime, boolean collective) {
        return null;
    }
}