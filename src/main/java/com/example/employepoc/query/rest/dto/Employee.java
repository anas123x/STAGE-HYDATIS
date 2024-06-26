package com.example.employepoc.query.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employee")
@Builder
public class Employee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String name;
    private String position;
    private boolean deleted=false;

    // getters and setters
}