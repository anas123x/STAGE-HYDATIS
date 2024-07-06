package com.example.employepoc.command.rest.requests;

import lombok.Data;
import org.joda.time.LocalDate;

import java.util.List;

@Data
public class CreatePersonsCheckingWithCollectiveRequest {
    private List<Long> personIds;
    private LocalDate date;
    private String threeDaysTime;
    private boolean collective;
}
