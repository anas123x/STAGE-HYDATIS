package com.example.employepoc.commands;

import com.example.employepoc.command.commands.*;
import com.example.employepoc.command.domain.CheckingAggregate;
import com.example.employepoc.command.exceptions.CheckingNotFoundException;
import com.example.employepoc.command.exceptions.PersonNotFoundException;
import com.example.employepoc.command.handlers.CheckingCommandHandlers;
import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.command.rest.dto.Person;
import com.example.employepoc.command.rest.repository.CheckingCommandRepository;
import com.example.employepoc.command.rest.repository.PersonCommandRepository;
import com.hydatis.cqrsref.handlers.EventSourcingHandler;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Test class for {@link CheckingCommandHandlers}.
 * This class contains unit tests for handling commands related to checking operations.
 */
@ExtendWith(MockitoExtension.class)
public class CheckingCommandHandlerTest {

    @Mock
    private EventSourcingHandler<CheckingAggregate> eventSourceHandler;

    @Mock
    private CheckingCommandRepository checkingCommandRepository;

    @Mock
    private PersonCommandRepository personCommandRepository;

    @InjectMocks
    private CheckingCommandHandlers checkingCommandHandlers;

    private Person person;
    private CreateCheckingCommand createCheckingCommand;
    private Checking checking;

    /**
     * Sets up the test environment before each test.
     * Initializes test data and mock responses.
     */
    @BeforeEach
    void setUp() {
        person = Person.builder()
                .id(UUID.randomUUID().toString())
                .matricule("MAT001")
                .build();

        checking = Checking.builder()
                .actualTime(LocalDateTime.now())
                .direction(Checking.CheckingDirection.IN)
                .actualSource(Checking.CheckingSource.USER)
                .build();

        createCheckingCommand = CreateCheckingCommand.builder()
                .personId(person.getId())
                .checking(checking)
                .others(new ArrayList<>())
                .build();
    }

    /**
     * Tests successful handling of the CreateCheckingCommand.
     * Verifies that the correct methods are called and the checking ID is set as expected.
     */
    @Test
    void handle_CreateCheckingCommand_Success() {
        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(checkingCommandRepository.save(any(Checking.class))).thenAnswer(invocation -> {
            Checking savedChecking = invocation.getArgument(0);
            savedChecking.setId(UUID.randomUUID().toString());
            return savedChecking;
        });

        checkingCommandHandlers.handle(createCheckingCommand);

        verify(personCommandRepository, times(1)).findById(person.getId());
        verify(checkingCommandRepository, times(1)).save(any(Checking.class));
        verify(eventSourceHandler, times(1)).save(any(CheckingAggregate.class));

        assertEquals(checking.getId(), createCheckingCommand.getChecking().getId());
    }

    /**
     * Tests handling of the CreateCheckingCommand when the specified person is not found.
     * Verifies that a PersonNotFoundException is thrown.
     */
    @Test
    void handle_CreateCheckingCommand_PersonNotFound() {
        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> checkingCommandHandlers.handle(createCheckingCommand));

        verify(personCommandRepository, times(1)).findById(person.getId());
        verify(checkingCommandRepository, times(0)).save(any(Checking.class));
        verify(eventSourceHandler, times(0)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests successful handling of the DeletePersonCheckingCommand.
     * Verifies that the correct methods are called for deletion.
     */
    @Test
    void handle_DeletePersonChecking_Success() {
        Checking checkingToDelete = Checking.builder()
                .id(UUID.randomUUID().toString())
                .person(person)
                .actualTime(LocalDateTime.now())
                .direction(Checking.CheckingDirection.IN)
                .actualSource(Checking.CheckingSource.USER)
                .build();

        DeletePersonCheckingCommand deleteCommand = DeletePersonCheckingCommand.builder()
                .checking(checkingToDelete)
                .duplicate(false)
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(checkingCommandRepository.findByPersonIdAndActualTimeAndDirectionAndActualSource(
                anyString(), any(), any(), any()))
                .thenReturn(List.of(checkingToDelete));

        checkingCommandHandlers.handle(deleteCommand);

        verify(checkingCommandRepository, times(1)).save(any(Checking.class));
        verify(eventSourceHandler, times(1)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests handling of the DeletePersonCheckingCommand when the specified person is not found.
     * Verifies that a PersonNotFoundException is thrown.
     */
    @Test
    void handle_DeletePersonChecking_PersonNotFound() {
        Checking checkingToDelete = Checking.builder()
                .person(person)
                .actualTime(LocalDateTime.now())
                .direction(Checking.CheckingDirection.IN)
                .actualSource(Checking.CheckingSource.USER)
                .build();

        DeletePersonCheckingCommand deleteCommand = DeletePersonCheckingCommand.builder()
                .checking(checkingToDelete)
                .duplicate(false)
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> checkingCommandHandlers.handle(deleteCommand));

        verify(checkingCommandRepository, times(0)).save(any(Checking.class));
        verify(eventSourceHandler, times(0)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests successful handling of the CreateOrUpdatePersonCheckingCommand.
     * Verifies that existing checkings are updated correctly.
     */
    @Test
    void handle_CreateOrUpdatePersonChecking_Success() {
        String checkingId = UUID.randomUUID().toString();
        Checking existingChecking = Checking.builder()
                .id(checkingId)
                .person(person)
                .actualTime(LocalDateTime.now())
                .direction(Checking.CheckingDirection.IN)
                .actualSource(Checking.CheckingSource.USER)
                .build();

        CreateOrUpdatePersonCheckingCommand updateCommand = CreateOrUpdatePersonCheckingCommand.builder()
                .checkingId(checkingId)
                .personId(person.getId())
                .date(LocalDateTime.now().toLocalDate())
                .threeDaysTime("3 days")
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(checkingCommandRepository.findById(checkingId)).thenReturn(Optional.of(existingChecking));
        when(checkingCommandRepository.save(any(Checking.class))).thenReturn(existingChecking);

        checkingCommandHandlers.handle(updateCommand);

        verify(checkingCommandRepository, times(1)).save(any(Checking.class));
        verify(eventSourceHandler, times(1)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests handling of the CreateOrUpdatePersonCheckingCommand when the specified checking is not found.
     * Verifies that a CheckingNotFoundException is thrown.
     */
    @Test
    void handle_CreateOrUpdatePersonChecking_CheckingNotFound() {
        CreateOrUpdatePersonCheckingCommand updateCommand = CreateOrUpdatePersonCheckingCommand.builder()
                .checkingId(UUID.randomUUID().toString())
                .personId(person.getId())
                .date(new LocalDate())
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(checkingCommandRepository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(CheckingNotFoundException.class, () -> checkingCommandHandlers.handle(updateCommand));

        verify(checkingCommandRepository, times(0)).save(any(Checking.class));
        verify(eventSourceHandler, times(0)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests successful handling of the CreatePersonsCheckingCommand.
     * Verifies that checkings for multiple persons are created successfully.
     */
    @Test
    void handle_CreatePersonsChecking_Success() {
        List<String> personIds = List.of(person.getId());
        CreatePersonsCheckingCommand createPersonsCommand = CreatePersonsCheckingCommand.builder()
                .personIds(personIds)
                .date(LocalDateTime.now().toLocalDate())
                .threeDaysTime("3 days")
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.of(person));
        when(checkingCommandRepository.save(any(Checking.class))).thenAnswer(invocation -> {
            Checking savedChecking = invocation.getArgument(0);
            savedChecking.setId(UUID.randomUUID().toString());
            return savedChecking;
        });

        checkingCommandHandlers.handle(createPersonsCommand);

        verify(personCommandRepository, times(1)).findById(person.getId());
        verify(checkingCommandRepository, times(1)).save(any(Checking.class));
        verify(eventSourceHandler, times(1)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests handling of the CreatePersonsCheckingCommand when a specified person is not found.
     * Verifies that a PersonNotFoundException is thrown.
     */
    @Test
    void handle_CreatePersonsChecking_PersonNotFound() {
        List<String> personIds = List.of(person.getId());
        CreatePersonsCheckingCommand createPersonsCommand = CreatePersonsCheckingCommand.builder()
                .personIds(personIds)
                .date(LocalDateTime.now().toLocalDate())
                .threeDaysTime("3 days")
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> checkingCommandHandlers.handle(createPersonsCommand));

        verify(checkingCommandRepository, times(0)).save(any(Checking.class));
        verify(eventSourceHandler, times(0)).save(any(CheckingAggregate.class));
    }

    /**
     * Tests successful handling of the CreatePersonsCheckingWithCollectiveCommand.
     * Verifies that collective checkings for multiple persons are created successfully.
     */
    @Test
    void handle_CreatePersonsCheckingWithCollective_Success() {
        // Setup command with multiple person IDs
        List<String> personIds = List.of(person.getId());
        CreatePersonsCheckingWithCollectiveCommand command = CreatePersonsCheckingWithCollectiveCommand.builder()
                .personIds(personIds)
                .date(LocalDate.now()) // Adjust to LocalDate as per handler
                .threeDaysTime("3 days")
                .collective(true)
                .build();

        // Mock repository findById to return the person for each ID
        when(personCommandRepository.findById(anyString())).thenReturn(Optional.of(person));

        // Mock repository saveAll to return the input list
        when(checkingCommandRepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute the handler with the command
        checkingCommandHandlers.handle(command);

        // Verify findById was called for each person ID
        personIds.forEach(id -> verify(personCommandRepository, times(1)).findById(id));

        // Verify saveAll was called with a list of Checkings
        verify(checkingCommandRepository, times(1)).saveAll(anyList());

        // Verify eventSourceHandler.save was called with a CheckingAggregate
        verify(eventSourceHandler, times(1)).save(any(CheckingAggregate.class));
    }
    /**
     * Tests handling of the CreatePersonsCheckingWithCollectiveCommand when a specified person is not found.
     * Verifies that a PersonNotFoundException is thrown.
     */
    @Test
    void handle_CreatePersonsCheckingWithCollective_PersonNotFound() {
        List<String> personIds = List.of(person.getId());
        CreatePersonsCheckingWithCollectiveCommand command = CreatePersonsCheckingWithCollectiveCommand.builder()
                .personIds(personIds)
                .date(LocalDateTime.now().toLocalDate())
                .threeDaysTime("3 days")
                .collective(true)
                .build();

        when(personCommandRepository.findById(person.getId())).thenReturn(Optional.empty());

        assertThrows(PersonNotFoundException.class, () -> checkingCommandHandlers.handle(command));

        verify(checkingCommandRepository, times(0)).save(any(Checking.class));
        verify(eventSourceHandler, times(0)).save(any(CheckingAggregate.class));
    }
}