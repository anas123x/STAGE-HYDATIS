package com.example.employepoc.queries;

import com.example.employepoc.command.exceptions.UserNotFoundException;
import com.example.employepoc.query.handlers.CheckingQueryHandler;
import com.example.employepoc.query.queries.*;
import com.example.employepoc.query.rest.dto.Checking;
import com.example.employepoc.query.rest.dto.Person;
import com.example.employepoc.query.rest.repository.CheckingQueryRepository;
import com.example.employepoc.query.rest.repository.PersonQueryRepository;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link CheckingQueryHandler} class.
 * These tests verify the functionality of querying checkings and persons from repositories,
 * ensuring correct handling and mapping of data.
 */
class CheckingQueryHandlerTest {

    private CheckingQueryRepository checkingQueryRepository;
    private PersonQueryRepository personQueryRepository;
    private CheckingQueryHandler checkingQueryHandler;

    /**
     * Sets up the test environment before each test.
     * This includes mocking the {@link CheckingQueryRepository} and {@link PersonQueryRepository},
     * and initializing the {@link CheckingQueryHandler} with these mocks.
     */
    @BeforeEach
    void setUp() {
        checkingQueryRepository = mock(CheckingQueryRepository.class);
        personQueryRepository = mock(PersonQueryRepository.class);
        checkingQueryHandler = new CheckingQueryHandler(checkingQueryRepository, personQueryRepository);
    }

    /**
     * Tests the retrieval of checkings for a single person within a specified date range.
     * Verifies that the correct checking is returned for the person and date range provided.
     */
    @Test
    void handle_GetPersonCheckingsQuery() {
        GetPersonCheckingsQuery query = new GetPersonCheckingsQuery(1L, new LocalDateTime(2023, 1, 1, 0, 0), new LocalDateTime(2023, 12, 31, 23, 59));
        Person person = Person.builder().id(1L).firstName("John").lastName("Doe").build();
        Checking checking = Checking.builder().id("1").person(person).actualTime(new LocalDateTime(2023, 6, 15, 0, 0)).build();

        when(checkingQueryRepository.findAll()).thenReturn(Collections.singletonList(checking));

        List<Checking> result = checkingQueryHandler.handle(query);

        assertEquals(1, result.size());
        assertEquals(checking, result.get(0));
    }

    /**
     * Tests the retrieval of checkings for multiple persons within a specified date range.
     * Verifies that the correct checkings are returned for each person and that the results are correctly mapped by person ID.
     */
    @Test
    void handle_GetPersonsCheckingsQuery() {
        GetPersonsCheckingsQuery query = new GetPersonsCheckingsQuery(Arrays.asList(1L, 2L), new LocalDateTime(2023, 1, 1, 0, 0), new LocalDateTime(2023, 12, 31, 23, 59));
        Person person1 = Person.builder().id(1L).firstName("John").lastName("Doe").build();
        Person person2 = Person.builder().id(2L).firstName("Jane").lastName("Doe").build();
        Checking checking1 = Checking.builder().id("1").person(person1).actualTime(new LocalDateTime(2023, 6, 15, 0, 0)).build();
        Checking checking2 = Checking.builder().id("2").person(person2).actualTime(new LocalDateTime(2023, 6, 16, 0, 0)).build();

        when(checkingQueryRepository.findAll()).thenReturn(Arrays.asList(checking1, checking2));

        Map<Long, List<Checking>> result = checkingQueryHandler.handle(query);

        assertEquals(2, result.size());
        assertTrue(result.containsKey(1L));
        assertTrue(result.containsKey(2L));
        assertEquals(1, result.get(1L).size());
        assertEquals(1, result.get(2L).size());
    }

    /**
     * Tests the retrieval of checkings by dates for multiple persons.
     * Verifies that the checkings are correctly grouped by date and then mapped by person ID.
     */
    @Test
    void handle_GetUserCheckingsByDatesAndPersonsMapQuery() {
        GetUserCheckingsByDatesAndPersonsMapQuery query = new GetUserCheckingsByDatesAndPersonsMapQuery(Arrays.asList(1L, 2L),Arrays.asList(new LocalDate(2023, 6, 15)));
        Person person1 = Person.builder().id(1L).firstName("John").lastName("Doe").build();
        Person person2 = Person.builder().id(2L).firstName("Jane").lastName("Doe").build();
        Checking checking1 = Checking.builder().id("1").person(person1).actualTime(new LocalDateTime(2023, 6, 15, 0, 0)).build();
        Checking checking2 = Checking.builder().id("2").person(person2).actualTime(new LocalDateTime(2023, 6, 15, 0, 0)).build();

        when(personQueryRepository.findById(1L)).thenReturn(Optional.of(person1));
        when(personQueryRepository.findById(2L)).thenReturn(Optional.of(person2));
        when(checkingQueryRepository.findAll()).thenReturn(Arrays.asList(checking1, checking2));

        Map<LocalDate, Map<Long, List<Checking>>> result = checkingQueryHandler.handle(query);
        LocalDate testDate = new LocalDate(2023, 6, 15);

        assertEquals(1, result.size());
        assertTrue(result.containsKey(testDate));
        assertEquals(2, result.get(testDate).size());
        assertTrue(result.get(testDate).containsKey(1L));
        assertTrue(result.get(testDate).containsKey(2L));
    }

    /**
     * Tests the retrieval of checkings by persons for multiple dates.
     * Verifies that the checkings are correctly grouped by person ID and then mapped by date.
     */
    @Test
    void handle_GetUserCheckingsByPersonsAndDatesMapQuery() {
        GetUserCheckingsByPersonsAndDatesMapQuery query = new GetUserCheckingsByPersonsAndDatesMapQuery(Arrays.asList(1L, 2L), Arrays.asList(new LocalDate(2023, 6, 15)));
        Person person1 = Person.builder().id(1L).firstName("John").lastName("Doe").build();
        Person person2 = Person.builder().id(2L).firstName("Jane").lastName("Doe").build();
        Checking checking1 = Checking.builder().id("1").person(person1).actualTime(new LocalDateTime(2023, 6, 15, 0, 0)).build();
        Checking checking2 = Checking.builder().id("2").person(person2).actualTime(new LocalDateTime(2023, 6, 15, 0, 0)).build();
        LocalDate testDate = new LocalDate(2023, 6, 15);
        when(personQueryRepository.findById(1L)).thenReturn(Optional.of(person1));
        when(personQueryRepository.findById(2L)).thenReturn(Optional.of(person2));
        when(checkingQueryRepository.findAll()).thenReturn(Arrays.asList(checking1, checking2));

        Map<Long, Map<LocalDate, List<Checking>>> result = checkingQueryHandler.handle(query);

        assertEquals(2, result.size());
        assertTrue(result.containsKey(1L));
        assertTrue(result.containsKey(2L));
        assertEquals(1, result.get(1L).size());
        assertEquals(1, result.get(2L).size());
        assertTrue(result.get(1L).containsKey(testDate));
        assertTrue(result.get(2L).containsKey(testDate));
    }
}