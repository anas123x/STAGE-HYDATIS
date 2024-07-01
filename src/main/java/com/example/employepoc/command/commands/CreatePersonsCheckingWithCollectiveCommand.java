package com.example.employepoc.command.commands;

import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.LocalDate;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePersonsCheckingWithCollectiveCommand extends BaseCommand {
    private String id;
    private List<Long> personIds;
    private LocalDate date;
    private String threeDaysTime;
    private boolean collective;
}