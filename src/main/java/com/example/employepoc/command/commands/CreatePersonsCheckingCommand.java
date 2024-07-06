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
 * Command for creating checking records for multiple persons.
 * This command encapsulates the necessary information for creating checking records,
 * including a unique identifier for the command, a list of person IDs, the date of the checking,
 * and a custom field possibly indicating a time span or specific timing.
 */
@Data
@AllArgsConstructor // Generates constructor with all arguments
@NoArgsConstructor // Generates no-arguments constructor
@Builder // Provides a builder pattern for object construction
public class CreatePersonsCheckingCommand extends BaseCommand {
    private String id; // Unique identifier for the command
    private List<Long> personIds; // IDs of the persons associated with the checking
    private LocalDate date;
    private String threeDaysTime;
    private List<Checking> checkings; // List of checking records for each person
}