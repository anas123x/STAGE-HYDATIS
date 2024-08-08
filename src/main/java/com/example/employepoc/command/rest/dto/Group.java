package com.example.employepoc.command.rest.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;


/**
 * Represents a group entity in the system.
 * This class extends {@link BaseBean} and implements {@link Serializable} to support serialization.
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
@Table(name = "groups")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group extends BaseBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String groupId;
    private String parentGroupId;
    @Column(unique = true)
    private String name;
    private String comment;
    private Boolean ghost = false;
    private String externalId;
    private String externalParentGroupId;
    @Column(unique = true)
    private String groupCode;

    @OneToOne
    private Person manager;

    private boolean nonSynchronizable;


    @Override
    public Group clone() {
        try {
            Group cloned = (Group) super.clone();
            cloned.setGroupId(UUID.randomUUID().toString());

            if (this.manager != null) {
                cloned.setManager(this.manager.clone());
            }

            cloned.setParentGroupId(this.parentGroupId);
            cloned.setName(this.name + "-cloned");
            cloned.setComment(this.comment);
            cloned.setGhost(this.ghost);
            cloned.setExternalId(this.externalId);
            cloned.setExternalParentGroupId(this.externalParentGroupId);
            cloned.setGroupCode(this.groupCode + "-cloned");
            cloned.setNonSynchronizable(this.nonSynchronizable);

            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
