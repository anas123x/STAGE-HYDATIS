package com.example.employepoc.command.rest.dto;

import com.hydatis.cqrsref.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Entity class representing a Person in the system.
 * Persons are associated with various attributes including their name, matricule gender etc.
 */

@Entity
@Table(name = "person")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 3L;
    @Id
    private String id;
    private String firstName;
    private String secondName;
    private String lastName;
    @Column(unique = true)
    private String matricule;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    @Column(unique = true)
    private String phoneNumber;
    private String activity;
    private String title;
    private Date employDate;
    private Date birthDate;
    private String card;
    @Column(unique = true)
    private String email;
    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "groupId")
    private Group group;
    @OneToMany
    private List<Contract> contracts;
    @Column(unique = true)
    private String hardId;
    @Column(unique = true)
    private String CIN;
    private String PersonnePrevenir;
    private String GroupeSanguin;
    private String MedecinTraitant;
    private String NumUrgence;
    private String Maladie;
    private String Affectation;
    @OneToOne
    private User user;
    private boolean nonSynchronizable;
    @ElementCollection
    @CollectionTable(name = "Person_map", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "key")
    @Column(name = "value")
    private Map<String, String> attributes;

    @Override
    public String toString() {
        return String.format("Person(id=%s, matricule=%s, %s %s)", getId(), matricule, firstName, lastName);
    }

    public Person clone() {
        Person cloned = new Person();
        cloned.setId(UUID.randomUUID().toString()); // Assign a new unique ID
        cloned.setFirstName(this.firstName);
        cloned.setSecondName(this.secondName);
        cloned.setLastName(this.lastName);
        cloned.setMatricule(this.matricule + "_copy");
        cloned.setGender(this.gender);
        cloned.setAddress(this.address);
        cloned.setPhoneNumber(this.phoneNumber + "_copy");
        cloned.setActivity(this.activity);
        cloned.setTitle(this.title);
        cloned.setEmployDate((this.employDate != null) ? (Date) this.employDate.clone() : null);
        cloned.setBirthDate((this.birthDate != null) ? (Date) this.birthDate.clone() : null);
        cloned.setCard(this.card);
        cloned.setEmail(this.email + "_copy");
        cloned.setHardId(this.hardId + "_copy");
        cloned.setCIN(this.CIN + "_copy");
        cloned.setPersonnePrevenir(this.PersonnePrevenir);
        cloned.setGroupeSanguin(this.GroupeSanguin);
        cloned.setMedecinTraitant(this.MedecinTraitant);
        cloned.setNumUrgence(this.NumUrgence);
        cloned.setMaladie(this.Maladie);
        cloned.setAffectation(this.Affectation);
        cloned.setNonSynchronizable(this.nonSynchronizable);
        cloned.setAttributes((this.attributes != null) ? new HashMap<>(this.attributes) : null);
        return cloned;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Use only the unique identifier
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }
}
