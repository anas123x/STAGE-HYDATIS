package com.example.employepoc.command.commands;

import com.example.employepoc.command.rest.dto.Checking;
import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.*;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Command for creating a checking record.
 * This command encapsulates the necessary information for creating a checking record,
 * including a unique identifier for the command, the ID of the person associated with the checking,
 * the primary checking details, and any additional checking records related to the same person.
 */
@Data // Generates getters, setters, equals, hashCode, and toString methods
@AllArgsConstructor // Generates constructor with all arguments
@NoArgsConstructor // Generates no-arguments constructor
@Builder // Provides a builder pattern for object construction
@ToString // Generates a toString method including all class attributes
public class CreateCheckingCommand extends BaseCommand {
    private String id; // Unique identifier for the command
    private String personId; // ID of the person associated with the checking
    private Checking checking; // Primary checking details
    private ArrayList<Checking> others; // Additional checking records related to the person
}