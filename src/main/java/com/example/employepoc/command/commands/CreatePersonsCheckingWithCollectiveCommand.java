package com.example.employepoc.command.commands;

import com.example.employepoc.command.rest.dto.Checking;
import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

import java.util.List;

/**
 * Command for creating checking records for multiple persons with collective attributes.
 * This command encapsulates the necessary information for creating checking records for a group of persons,
 * including a unique identifier for the command, a list of person IDs, the date of the checking,
 * a custom field indicating a time span or specific timing, and a flag indicating if the checking is collective.
 */
@Data
@AllArgsConstructor // Generates constructor with all arguments
@NoArgsConstructor // Generates no-arguments constructor
@Builder // Provides a builder pattern for object construction
public class CreatePersonsCheckingWithCollectiveCommand extends BaseCommand {
    private String id; // Unique identifier for2 the command
    private List<Long> personIds; // IDs of the persons associated with the checking
    private LocalDate date; // Date of the checking event
    private String threeDaysTime; // Custom field, possibly indicating a time span or specific timing
    private boolean collective; // Flag indicating if the checking is collective
    private List<Checking> checkings;
}