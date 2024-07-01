package com.example.employepoc.command.rest.service;

import com.example.employepoc.command.rest.dto.Checking;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface ICheckingCommandService {
    public Checking createChecking(LocalDateTime localDateTime, Long personId, Checking.CheckingDirection cd,
                                   Checking.CheckingSource s, ArrayList<Checking> others);

    void deletePersonChecking(Checking checking, boolean duplicate);
    Checking createOrUpdatePersonChecking(String id, Long personId, LocalDate date, String threeDaysTime);

    List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime);


    List<Checking> createPersonsChecking(List<Long> personIds, LocalDate date, String threeDaysTime, boolean collective);
}
