package com.example.employepoc.command.commands;

import com.example.employepoc.command.rest.dto.Employee;
import com.hydatis.cqrsref.commands.BaseCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteEmployeeCommand extends BaseCommand {
    private String id;
    private Employee employee;
}
