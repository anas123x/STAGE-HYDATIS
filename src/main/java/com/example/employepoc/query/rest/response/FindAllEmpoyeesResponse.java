package com.example.employepoc.query.rest.response;

import com.example.employepoc.query.rest.dto.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class FindAllEmpoyeesResponse extends BaseResponse{
List<Employee> employees;
public FindAllEmpoyeesResponse(String message) {
super(message);
}
}
