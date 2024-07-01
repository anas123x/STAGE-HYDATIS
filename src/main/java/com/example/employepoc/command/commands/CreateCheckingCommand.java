package com.example.employepoc.command.commands;

import com.example.employepoc.command.rest.dto.Checking;
import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.*;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateCheckingCommand extends BaseCommand {
    private String id;
    private Long personId;
    private Checking checking;
    private ArrayList<Checking> others;
}