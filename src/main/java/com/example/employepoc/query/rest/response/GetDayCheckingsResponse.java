package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Checking;

import java.util.Collection;

public class GetDayCheckingsResponse extends BaseResponse{
    private Collection<Checking> checkings;

    public GetDayCheckingsResponse(String message, Collection<Checking> checkings) {
        super(message);
        this.checkings = checkings;
    }

    public Collection<Checking> getCheckings() {
        return checkings;
    }

    public void setCheckings(Collection<Checking> checkings) {
        this.checkings = checkings;
    }
}
