package com.example.employepoc.command.exceptions;
/**
 * Represents an exception that is thrown when an error occurs during the deletion of a checking operation.
 * This custom exception class is designed to encapsulate errors specific to the deletion process of a checking entity,
 * allowing for more granular error handling and reporting.
 *
 * The exception carries a message detailing the reason for the error, which can be used for logging or to inform the user
 * of the nature of the error in a more controlled and meaningful way.
 */
public class CheckingDeletedException extends RuntimeException{
    public CheckingDeletedException(String message) {
        super(message);
    }
}
