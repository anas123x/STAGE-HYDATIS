package com.example.employepoc.command.rest.service;

import com.example.employepoc.command.rest.dto.Checking;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface defining operations for managing checkings related to persons.
 */
public interface ICheckingCommandService {

    /**
     * Creates a new checking instance.
     *
     * @param localDateTime The date and time of the checking.
     * @param personId The ID of the person associated with the checking.
     * @param cd The direction of the checking.
     * @param s The source of the checking.
     * @param others A list of other related checkings.
     * @return The created Checking instance.
     */
    Checking createChecking(LocalDateTime localDateTime, Long personId, Checking.CheckingDirection cd,
                            Checking.CheckingSource s, ArrayList<Checking> others);

    /**
     * Deletes a checking associated with a person.
     *
     * @param checking The checking to delete.
     * @param duplicate A flag indicating if duplicates should be considered.
     */
    void deletePersonChecking(Checking checking, boolean duplicate);

    /**
     * Creates or updates a checking for a person.
     *
     * @param id The ID of the checking to update, or null to create a new one.
     * @param personId The ID of the person.
     * @param date The date of the checking.
     * @param threeDaysTime A string representation of a time span related to the checking.
     * @return The created or updated Checking instance.
     */
    Checking createOrUpdatePersonChecking(String id, Long personId, LocalDateTime date, String threeDaysTime);

    /**
     * Creates checkings for multiple persons.
     *
     * @param personIds A list of person IDs.
     * @param date The date of the checkings.
     * @param threeDaysTime A string representation of a time span related to the checkings.
     * @return A list of created Checking instances.
     */
    List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime);

    /**
     * Creates checkings for multiple persons, with an option for collective processing.
     *
     * @param personIds A list of person IDs.
     * @param date The date of the checkings.
     * @param threeDaysTime A string representation of a time span related to the checkings.
     * @param collective A flag indicating if the checkings should be processed as a collective action.
     * @return A list of created Checking instances.
     */
    List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime, boolean collective);
}