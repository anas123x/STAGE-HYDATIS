package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindEmployeeByIdResponse extends BaseResponse{
    Employee employee;
    public FindEmployeeByIdResponse(String message, Employee employee) {
        super(message);
        this.employee=employee;
    }
}
