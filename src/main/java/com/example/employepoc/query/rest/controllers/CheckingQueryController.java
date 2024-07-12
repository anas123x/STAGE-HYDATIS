package com.example.employepoc.query.rest.controllers;

import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;

import com.example.employepoc.query.rest.response.*;
import com.hydatis.cqrsref.infrastructure.IQueryDispatcher;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Controller for handling queries related to checkings.
 * This controller provides endpoints for retrieving checkings for a single person or multiple persons
 * within a specified date range.
 */
@RestController
@RequestMapping("/checking")
public class CheckingQueryController {

    @Autowired
    private IQueryDispatcher queryDispatcher;
    private static final org.joda.time.format.DateTimeFormatter formatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final org.joda.time.format.DateTimeFormatter DateFormatter = org.joda.time.format.DateTimeFormat.forPattern("yyyy-MM-dd");

    /**
     * Retrieves checkings for a single person within a specified date range.
     * @param personId The ID of the person whose checkings are to be retrieved.
     * @param from The start date of the range (inclusive).
     * @param to The end date of the range (inclusive).
     * @return ResponseEntity containing the checkings and a success message.
     */
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

    /**
     * Retrieves checkings for multiple persons within a specified date range.
     * @param personsId A comma-separated string of person IDs whose checkings are to be retrieved.
     * @param from The start date of the range (inclusive).
     * @param to The end date of the range (inclusive).
     * @return ResponseEntity containing the checkings mapped by person ID and a success message.
     */
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

    /**
     * Retrieves checkings for a person on a specific day.
     * @param personId The ID of the person whose checkings are to be retrieved.
     * @param date The specific date for which checkings are requested.
     * @return ResponseEntity containing the checkings for the specified day and a success message.
     */
    @GetMapping("/dayCheckings/{personId}")
    public ResponseEntity<GetDayCheckingsResponse> getDayCheckings(
            @PathVariable Long personId,
            @RequestParam("date") String date) {

        LocalDate localDate = LocalDate.parse(date, DateFormatter);

        List<Checking> checkings = queryDispatcher.send(new GetDayCheckingsQuery(personId, localDate));

        var response = GetDayCheckingsResponse.builder()
                .checkings(checkings)
                .message("Checkings retrieved successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Retrieves collective checkings for a person on a specific day.
     * @param personId The ID of the person whose checkings are to be retrieved.
     * @param date The specific date for which checkings are requested.
     * @return ResponseEntity containing the checkings for the specified day and a success message.
     */
    @GetMapping("/collective/{personId}")
    public ResponseEntity<GetDayCheckingsResponse> getCollectiveCheckings(
            @PathVariable long personId,
            @RequestParam("date") String date
    ){
        LocalDate localDate = LocalDate.parse(date,DateFormatter);
        List<Checking> checkings = queryDispatcher.send(new GetCollectiveCheckingsQuery(personId, localDate));
        var response = GetDayCheckingsResponse.builder()
                .checkings(checkings)
                .message("Checkings retrieved successfully")
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Retrieves checkings for multiple persons across multiple dates, organized by date then person ID.
     * @param personsIds A comma-separated string of person IDs.
     * @param dates A comma-separated string of dates.
     * @return ResponseEntity containing the checkings organized by date and person ID, along with a success message.
     */
    @GetMapping("/userCheckingsByDatesAndPersonsMap")
    public ResponseEntity<GetUserCheckingsByDatesAndPersonsMapResponse> getUserCheckingsByDatesAndPersonsMap(
            @RequestParam("personsIds") String personsIds,
            @RequestParam("dates") String dates) {
        List<Long> personsIdsList = Arrays.stream(personsIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        Collection<LocalDate> datesList = Arrays.stream(dates.split(",")).toList().stream()
                .map(LocalDate::parse)
                .collect(Collectors.toList());

        GetUserCheckingsByDatesAndPersonsMapQuery query = new GetUserCheckingsByDatesAndPersonsMapQuery(personsIdsList, datesList);
        Map<LocalDate, Map<Long, List<Checking>>> checkings =(Map<LocalDate, Map<Long, List<Checking>>>) queryDispatcher.sendObject(query);
        var response = GetUserCheckingsByDatesAndPersonsMapResponse.builder()
                .checkings(checkings)
                .message("Checkings retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Retrieves checkings for multiple persons across multiple dates, organized by person ID then date.
     * @param personsIds A comma-separated string of person IDs.
     * @param dates A comma-separated string of dates.
     * @return ResponseEntity containing the checkings organized by person ID and date, along with a success message.
     */
    @GetMapping("/userCheckingsByPersonsAndDatesMap")
    public ResponseEntity<GetUserCheckingsByPersonsAndDatesMapResponse> getUserCheckingsByPersonsAndDatesMap(
            @RequestParam("personsIds") String personsIds,
            @RequestParam("dates") String dates) {
        List<Long> personsIdsList = Arrays.stream(personsIds.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());
        Collection<LocalDate> datesList = Arrays.stream(dates.split(",")).toList().stream()
                .map(LocalDate::parse)
                .collect(Collectors.toList());
        GetUserCheckingsByPersonsAndDatesMapQuery query = new GetUserCheckingsByPersonsAndDatesMapQuery(personsIdsList,datesList);
        Map<Long, Map<LocalDate, List<Checking>>> checkings =(Map<Long, Map<LocalDate, List<Checking>>>) queryDispatcher.sendObject(query);
        var response = GetUserCheckingsByPersonsAndDatesMapResponse.builder()
                .checkings(checkings)
                .message("Checkings retrieved successfully")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    /**
     * Retrieves checkings for a user on a specific date.
     * @param personId The ID of the person whose checkings are to be retrieved.
     * @param date The specific date for which checkings are requested.
     * @return ResponseEntity containing the checkings for the specified date and a success message, or an error message if an exception occurs.
     */
    @GetMapping("userCheckings")
    public ResponseEntity<GetPersonCheckingsResponse> getUserCheckings(
            @RequestParam("personId") Long personId,
            @RequestParam("date") String date) {  try {
        LocalDate localDate = LocalDate.parse(date, DateFormatter);

          List<Checking> checkings = queryDispatcher.send(new GetUserCheckingsQuery(personId, localDate));
          var response = GetPersonCheckingsResponse.builder()
                  .checkings(checkings)
                  .message("Checkings retrieved successfully")
                  .build();
          return new ResponseEntity<>(response, HttpStatus.OK);

      }catch (Exception e){
          return  ResponseEntity.status(404).body(new GetPersonCheckingsResponse(e.getMessage(),null));
      }

    }

}