package com.example.employepoc.command.rest.dto;


import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;


/**
 * Represents a move entity in the system.
 * This class extends {@link BaseEntity} and implements {@link Serializable} to support serialization.
 * It is annotated as an {@link Entity} for ORM compatibility, specifically with JPA.
 *
 * @Entity Marks this class as a JPA entity.
 * @Table Specifies the table in the database with which this entity is mapped.
 * @AllArgsConstructor Lombok's annotation to generate a constructor with one parameter for each field.
 * @NoArgsConstructor Lombok's annotation to generate a no-arguments constructor.
 * @Builder Lombok's annotation to implement the builder pattern.
 * @Data Lombok's annotation to generate getters, setters, toString, equals, and hashCode methods based on fields.
 */
@Entity
@Table(name = "MoveEntity")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MoveEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String entityId;
    private String newGroupId;
    private String oldGroupId;
    private LocalDate startSmooth;
    private LocalDate endSmooth;
    @Enumerated(EnumType.STRING)
    private EntityType entityType;
    @Column(name = "event_date")
    private LocalDate when;
    private boolean isMoved = false;
    private boolean smooth = false;

    @Override
    public BaseBean clone() {
        return null;
    }

}
