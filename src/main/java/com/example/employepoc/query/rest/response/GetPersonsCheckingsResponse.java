package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonsCheckingsResponse extends BaseResponse {
    private Map<Long, List<Checking>> checkings;

    public GetPersonsCheckingsResponse(String message, Map<Long, List<Checking>> checkings) {
        super(message);
        this.checkings = checkings;
    }
}
