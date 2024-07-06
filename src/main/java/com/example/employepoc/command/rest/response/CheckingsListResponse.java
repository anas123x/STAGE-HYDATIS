package com.example.employepoc.command.rest.response;

import com.example.employepoc.command.rest.dto.Checking;
import com.example.employepoc.query.rest.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CheckingsListResponse  extends BaseResponse {
    private String message;
    private List<Checking> checkings;

    public CheckingsListResponse(String message) {
        this.message = message;
    }
}
