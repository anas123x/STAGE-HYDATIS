package com.example.employepoc.command.rest.service;

import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.dto.Person;
import com.example.employepoc.command.rest.repository.CheckingCommandRepository;
import com.example.employepoc.command.rest.repository.PersonCommandRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CheckingCommandService implements ICheckingCommandService{

    @Autowired
    private CheckingCommandRepository checkingCommandRepository;

    @Autowired
    private PersonCommandRepository personCommandRepository;

    @Override
    public Checking createChecking(LocalDateTime localDateTime, Long personId, Checking.CheckingDirection cd, Checking.CheckingSource s, ArrayList<Checking> others) {
        System.out.println("personID" + personId + "localDateTime" + localDateTime + "Checking" + cd + "sourec " + s + "other" + others);
        // Fetch the person from the database
        Person person = personCommandRepository.findById(personId).orElse(null);
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
    }

    public void deletePersonChecking(Checking checking, boolean duplicate) {
        System.out.println("Checking: " + checking + " Duplicate: " + duplicate);
        if (duplicate) {
            List<Checking> checkings = checkingCommandRepository.findByPersonIdAndActualTimeAndDirectionAndActualSource(
                    checking.getPerson().getId(),
                    checking.getActualTime(),
                    checking.getDirection(),
                    checking.getActualSource()
            );

            if (checkings.size() > 1) {

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
        } else {
            checking.setDeleted(true);
            checkingCommandRepository.save(checking);
        }
    }
    @Override
    public Checking createOrUpdatePersonChecking(String id, Long personId, LocalDate date, String threeDaysTime) {
        // Implement the logic to create or update a Person's Checking here
        // Fetch the Checking from the database
        Checking checking = checkingCommandRepository.findById(id).orElse(null);
        if (checking == null) {
            // Handle the case where the Checking is not found
            return null;
        }

        // Update the Checking
        checking.setLogicalTime(date.toDateTimeAtStartOfDay().toLocalDateTime());
        // Add any other necessary fields

        // Save the Checking to the database
        return checkingCommandRepository.save(checking);
    }

    @Override
    public List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime) {
        // Implement the logic to create Checkings for multiple Persons here
        // This will depend on your specific business logic
        return null;
    }

    @Override
    public List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime, boolean collective) {
        // Implement the logic to create Checkings for multiple Persons here, considering the 'collective' parameter
        // This will depend on your specific business logic
        return null;
    }
}