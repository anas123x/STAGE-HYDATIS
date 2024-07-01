package com.example.employepoc.command.rest.requests;

import com.example.employepoc.command.rest.dto.Checking;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DeletePersonCheckingRequest {
    private Checking checking;
    private boolean duplicate;
}
