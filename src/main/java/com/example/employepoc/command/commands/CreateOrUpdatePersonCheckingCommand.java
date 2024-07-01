package com.example.employepoc.command.commands;

import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrUpdatePersonCheckingCommand extends BaseCommand {
    private String id;
    private Long personId;
    private LocalDate date;
    private String threeDaysTime;
}