package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "employee")
@Builder
public class Employee extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String name;
    private String position;
    private boolean deleted=false;
}