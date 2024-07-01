package com.example.employepoc.query.rest.controllers;

import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.response.*;
import com.hydatis.cqrsref.domain.BaseEntity;
import com.hydatis.cqrsref.infrastructure.IQueryDispatcher;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/checkings")
public class CheckingQueryController {

    @Autowired
    private IQueryDispatcher queryDispatcher;
    private static final org.joda.time.format.DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    @GetMapping("/person/{personId}")
    public ResponseEntity<GetPersonCheckingsResponse> getPersonCheckings(
            @PathVariable Long personId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        LocalDateTime fromDate = LocalDateTime.parse(from, formatter);
        LocalDateTime toDate = LocalDateTime.parse(to, formatter);

        List<Checking> checkings = queryDispatcher.send(new GetPersonCheckingsQuery(personId, fromDate, toDate));

        var response = GetPersonCheckingsResponse.builder()
                .checkings(checkings)
                .message("Checkings retrieved successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/persons")
    public ResponseEntity<GetPersonsCheckingsResponse> getPersonsCheckings(
            @RequestParam("personsId") String personsId,
            @RequestParam("from") String from,
            @RequestParam("to") String to) {

        List<Long> personsIdList = Arrays.stream(personsId.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        LocalDateTime fromDate = LocalDateTime.parse(from, formatter);
        LocalDateTime toDate = LocalDateTime.parse(to, formatter);

        Map<Long, List<Checking>> checkings = (Map<Long, List<Checking>>) queryDispatcher.sendObject(new GetPersonsCheckingsQuery(personsIdList, fromDate, toDate));

        var response = GetPersonsCheckingsResponse.builder()
                .checkings(checkings)
                .message("Checkings retrieved successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
