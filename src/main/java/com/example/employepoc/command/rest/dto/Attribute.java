package com.example.employepoc.command.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * Represents an attribute entity with key-value pairs.
 * This class is annotated as an Entity, making it suitable for ORM (Object-Relational Mapping) frameworks like Hibernate.
 * It uses Lombok annotations to reduce boilerplate code for getter, setter, all-args constructor, and no-args constructor.
 */

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Attribute {
    @Id
    private String id;

    @Column(name = "key")
    private String key;

    @Column(name = "value")
    private String value;

}
