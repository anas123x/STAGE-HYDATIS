package com.example.employepoc.command.rest.requests;


import com.example.employepoc.command.rest.dto.Checking;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateCheckingRequest {
    private Checking checking;
    private Long personId;
    private ArrayList<Checking> others;
}
