package com.example.employepoc.command.commands;

import com.example.employepoc.command.rest.dto.Checking;
import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Command for deleting a person's checking record.
 * This command is used to encapsulate the necessary information for deleting a checking record,
 * including the unique identifier of the checking record, the checking details, and a flag indicating if the deletion is a duplicate action.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeletePersonCheckingCommand extends BaseCommand {
    private String id;
    private Checking checking;
    private boolean duplicate;
}