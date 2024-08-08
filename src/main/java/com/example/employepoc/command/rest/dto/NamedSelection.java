package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * Entity representing a named selection, which is a collection of people identified by a unique selection.
 * This entity extends {@link BaseEntity}, inheriting its basic entity properties.
 */
@Entity
@Table(name = "NamedSelection")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NamedSelection extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the named selection.
     */
    @Id
    private String id;

    /**
     * Name of the named selection.
     */
    private String name;

    /**
     * Unique login associated with the named selection.
     */
    @Column(unique = true)
    private String login;

    /**
     * Collection of people IDs associated with this named selection.
     * Stored as an element collection, representing a simple list of values in a separate table.
     */
    @ElementCollection
    @Column(name = "people_id")
    private List<String> peopleIDs;

    /**
     * List of {@link Person} entities associated with this named selection.
     * This association is not managed through a join table or foreign keys in this simplified example.
     */
    @OneToMany
    private List<Person> people;

    /**
     * Creates a deep copy of this NamedSelection instance.
     *
     * @return a new NamedSelection instance that is a copy of the current instance with "_copy" appended to its name.
     */
    @Override
    public NamedSelection clone() {
        NamedSelection res = new NamedSelection();
        res.setName(name + "_copy");
        res.setLogin(login + "_copy");
        res.setPeopleIDs(peopleIDs);
        res.setPeople(people);
        return res;
    }
}