package com.example.employepoc.query.rest.dto;

import com.example.employepoc.command.rest.dto.BaseBean;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a Person entity with various personal details.
 * This class is annotated with @Document, indicating it's a MongoDB document.
 */
@Getter
@Setter
@AllArgsConstructor
@Document(collection = "person")
@Builder
public class Person extends BaseBean {
	private static final long serialVersionUID = 3L;

	@Id
	private Long id;
	private String firstName;
	private String secondName;
	private String lastName;
	private String matricule;
	private Gender gender = Gender.MALE;
	private String adress;
	private String phoneNumber;
	private String activity;
	private String title;
	private Date employDate;
	private Date birthDate;
	private String card;
	private String email;
	// Additional fields commented out for brevity
	private String hardId;

	// Additional personal details added by developers
	private String CIN;
	private String PersonnePrevenir;
	private String GroupeSanguin;
	private String MedecinTraitant;
	private String NumUrgence;
	private String Maladie;
	private String Affectation;
	private User user;
	// Indicates if the person should not be synchronized with external systems
	private boolean nonSynchronizable;
	// Stores additional attributes as key-value pairs
	private Map<String, String> otherAttributes = new HashMap<String, String>();

	/**
	 * Default constructor.
	 */
	public Person() {
	}

	/**
	 * Removes an attribute from the otherAttributes map.
	 * @param attribute The key of the attribute to remove.
	 */
	public void removeAttribute(String attribute) {
		otherAttributes.remove(attribute);
	}

	/**
	 * Reads an attribute value from the otherAttributes map, returning a default if not found.
	 * @param attribute The key of the attribute to read.
	 * @param valueIfNull The default value to return if the attribute is not found.
	 * @return The value of the attribute or the default value if the attribute is not found.
	 */
	public String readAttribute(String attribute, String valueIfNull) {
		return otherAttributes.getOrDefault(attribute, valueIfNull);
	}

	/**
	 * Gets the otherAttributes map containing additional attributes.
	 * @return The otherAttributes map.
	 */
	public Map<String, String> getOtherAttributes() {
		return otherAttributes;
	}

	/**
	 * Sets the otherAttributes map to a new map of additional attributes.
	 * @param otherAttributes The new map of additional attributes.
	 */
	public void setOtherAttributes(Map<String, String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	/**
	 * Adds or updates an attribute in the otherAttributes map.
	 * @param attribute The key of the attribute to add or update.
	 * @param value The value of the attribute.
	 */
	public void addAttribute(String attribute, String value) {
		otherAttributes.put(attribute, value);
	}

	/**
	 * Edits an existing attribute in the otherAttributes map.
	 * @param attribute The key of the attribute to edit.
	 * @param value The new value of the attribute.
	 */
	public void editAttribute(String attribute, String value) {
		otherAttributes.put(attribute, value); // This effectively replaces the previous method's functionality
	}

	/**
	 * Constructor that sets the first name of the person.
	 * @param name The first name of the person.
	 */
	public Person(String name) {
		super();
		this.firstName = name;
	}

	/**
	 * Gets the hardId.
	 * @return The hardId.
	 */
	public String getHardId() {
		return hardId;
	}

	/**
	 * Sets the hardId.
	 * @param hardId The new hardId.
	 */
	public void setHardId(String hardId) {
		this.hardId = hardId;
	}

	/**
	 * Creates a deep copy of this Person object.
	 * @return A new Person object with copied attributes.
	 */
	@Override
	public Person clone() {
		Person res = new Person();
		res.setFirstName(firstName);
		res.setLastName(lastName);
		res.setSecondName(secondName);
		res.setMatricule(matricule);
		res.setHardId(hardId);
		res.setGender(gender);
		res.setAdress(adress);
		res.setActivity(activity);
		res.setPhoneNumber(phoneNumber);
		res.setBirthDate(birthDate);
		res.setEmployDate(employDate);
		res.setCIN(CIN);
		res.setPersonnePrevenir(PersonnePrevenir);
		res.setGroupeSanguin(GroupeSanguin);
		res.setMedecinTraitant(MedecinTraitant);
		res.setNumUrgence(NumUrgence);
		res.setMaladie(Maladie);
		res.setEmail(email);
		return res;
	}

	/**
	 * Gender enumeration for specifying gender.
	 */
	public enum Gender {
		MALE, FEMALE
	}

	/**
	 * Bureau enumeration for specifying the bureau location.
	 */
	public enum Bureau {
		Siège, Annexe, Etage, Agence, BoxDeChange
	}

	// Getters and setters omitted for brevity
}