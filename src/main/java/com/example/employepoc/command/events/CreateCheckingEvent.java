package com.example.employepoc.command.events;

import com.example.employepoc.command.rest.dto.Checking;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hydatis.cqrsref.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonSerialize
@ToString
public class CreateCheckingEvent extends BaseEvent {
    private String identifier;
    private Long personId;
    private Checking checking;
    private ArrayList<Checking> others;
}