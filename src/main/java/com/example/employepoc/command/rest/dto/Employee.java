package com.example.employepoc.command.rest.dto;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;
}