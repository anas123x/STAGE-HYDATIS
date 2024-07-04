package com.example.employepoc.command.commands;

import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

/**
 * Command for creating or updating a person's checking record.
 * This command encapsulates the necessary information for either creating a new checking record
 * or updating an existing one for a person, including a unique identifier for the command,
 * the ID of the person associated with the checking, the date of the checking,
 * and a custom field indicating a time span or specific timing.
 */
@Data
@AllArgsConstructor // Generates constructor with all arguments
@NoArgsConstructor // Generates no-arguments constructor
@Builder // Provides a builder pattern for object construction
public class CreateOrUpdatePersonCheckingCommand extends BaseCommand {
    private String id; // Unique identifier for the command
    private Long personId; // ID of the person associated with the checking
    private LocalDate date; // Date of the checking event
    private String threeDaysTime; // Custom field, possibly indicating a time span or specific timing
}