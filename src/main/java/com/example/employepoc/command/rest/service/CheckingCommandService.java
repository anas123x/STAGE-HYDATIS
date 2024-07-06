package com.example.employepoc.command.rest.service;

import com.example.employepoc.command.exceptions.CheckingCreatedException;
import com.example.employepoc.command.exceptions.CheckingDeletedException;
import com.example.employepoc.command.exceptions.CheckingNotFoundException;
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
    public Checking createOrUpdatePersonChecking(String id, Long personId, LocalDateTime date, String threeDaysTime) {
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
            return checkingCommandRepository.save(checking);
        }
        catch (PersonNotFoundException e) {
            throw new PersonNotFoundException("Error creating or updating checkingaa: " + e.getMessage());
        }
        catch (CheckingNotFoundException e) {
            throw new CheckingNotFoundException("Error creating or updating checkingxx: " + e.getMessage());
        }

        catch (Exception e) {
            System.out.println(e);
            throw new CheckingCreatedException("Error creating or updating checkingff: " + e.getMessage());
        }
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
        List<Checking> checkings = new ArrayList<>();
        for (Long personId : personIds) {
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
            } catch (Exception e) {
                throw new CheckingCreatedException("Error creating checkings for person ID " + personId + ": " + e.getMessage());
            }
        }
        return checkings;
    }

    @Override
    public List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime, boolean collective) {
        List<Checking> checkings = new ArrayList<>();
        for (Long personId : personIds) {
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
                dataMap.put("collective",collective ? "true" : "false");
                checking.setData(dataMap);



                checkings.add(checkingCommandRepository.save(checking));
            } catch (Exception e) {
                throw new CheckingCreatedException("Error creating checkings for person ID " + personId + ": " + e.getMessage());
            }
        }
        return checkings;
    }
}