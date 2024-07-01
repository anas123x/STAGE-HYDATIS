package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;
import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collection;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class GetPersonCheckingsResponse extends BaseResponse {
    private Collection<Checking> checkings;

    public GetPersonCheckingsResponse(String message, Collection<Checking> checkings) {
        super(message);
        this.checkings = checkings;
    }
}
