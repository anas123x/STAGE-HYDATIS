package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Checking;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDate;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize
public class PersonsCheckingCreatedWithCollectiveEvent extends BaseEvent {
    private String identifier;

    private List<Long> personIds;
    private LocalDate date;
    private String threeDaysTime;
    private boolean collective;
}