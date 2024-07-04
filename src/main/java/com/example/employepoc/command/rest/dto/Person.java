package com.example.employepoc.command.rest.dto;

import lombok.*;
import org.joda.time.LocalDate;


import javax.persistence.*;
import java.util.*;

//import tn.waycon.alquasar2.gp.association.model.PersonAffectedHard;


/**
 * Represents a person entity with various personal and professional details.
 * This class includes information such as name, gender, contact details, and employment information.
 * It also supports custom attributes for additional flexibility.
 */
@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "person")
@Builder
public class Person extends BaseBean implements Comparable<Person> {
	private static final long	  serialVersionUID	= 3L;

	@Id
	private Long id;
	private String	            firstName;

	private String	            secondName;
	private String	            lastName;
	private String	            matricule;
	private Gender	            gender	         = Gender.MALE;
	private String	            adress;
	private String	            phoneNumber;
	private String	            activity;
	private String	            title;
	private Date	              employDate;
	private Date	              birthDate;
	private String	            card;
	private String	            email;
/*	private Group	              group;
	private User	              user;
	private List<Contract>	    contracts	       = new ArrayList<Contract>();*/
	private String	            hardId;

	/*
	 * Ajouter Par Baha eddine wannes
	 */

	private String	            CIN;
	private String	            PersonnePrevenir;
	private String	            GroupeSanguin;
	private String	            MedecinTraitant;
	private String	            NumUrgence;
	private String	            Maladie;
	private String	            Affectation;

	// ------------------------------------------
	private boolean	            nonSynchronizable;
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "person_attributes")
	@MapKeyColumn(name = "attribute_name")
	@Column(name = "attribute_value")
	private Map<String, String>	otherAttributes	 = new HashMap<String, String>();

	public Person() {

	}

	public void removeAttribute(String attribute) {
		otherAttributes.remove(attribute);
	}

	public String readAttribute(String attribute, String valueIfNull) {
		return (otherAttributes.get(attribute) != null) ? otherAttributes.get(attribute) : valueIfNull;
	}

	public Map<String, String> getOtherAttributes() {
		return otherAttributes;
	}

	public void setOtherAttributes(Map<String, String> otherAttributes) {
		this.otherAttributes = otherAttributes;
	}

	public void addAttribute(String attribute, String value) {
		otherAttributes.put(attribute, value);

	}

	public void editAttribute(String attribute, String value) {
		otherAttributes.remove(attribute);
		otherAttributes.put(attribute, value);
	}

	public Person(String name) {
		super();
		this.firstName = name;
	}

/*	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}*/

	public String getHardId() {
		return hardId;
	}

	public void setHardId(String hardId) {
		this.hardId = hardId;
	}

/*	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}*/

	@Override
	public Person clone() {
		Person res = new Person();
		// res.setId(getId());//FIXME :fredj qui l' a mis
		res.setFirstName(firstName);
		res.setLastName(lastName);
		res.setSecondName(secondName);
		res.setMatricule(matricule);
		// ajouter par samar
		res.setHardId(hardId);
		//
		res.setGender(gender);

		res.setAdress(adress);
		res.setActivity(activity);
		res.setPhoneNumber(phoneNumber);
		res.setBirthDate(birthDate);
		res.setEmployDate(employDate);
		//res.setGroup(group);
		// ajouter par samar
		res.setCIN(CIN);
		res.setPersonnePrevenir(PersonnePrevenir);
		res.setGroupeSanguin(GroupeSanguin);
		res.setMedecinTraitant(MedecinTraitant);
		res.setNumUrgence(NumUrgence);
		res.setMaladie(Maladie);
		res.setEmail(email);

		// res.setExtraProperties(extraProperties);// FIXME

		return res;
	}

	// TODO update extraproperties
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondname) {
		this.secondName = secondname;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getEmployDate() {
		return employDate;
	}

	public void setEmployDate(Date employDate) {
		this.employDate = employDate;
	}

	public String getMatricule() {
		return matricule;
	}

	// @OneToMany(fetch = FetchType.EAGER)
	// public Set<ExtraProperty> getExtraProperties() {
	// return extraProperties;
	// }
	//
	// public void setExtraProperties(Set<ExtraProperty> extraProperties) {
	// this.extraProperties = extraProperties;
	// }

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// /* */ deleted by seif
/*	public boolean isActive(LocalDate when) {
		for (Contract contract : contracts) {
			if ((when.isAfter(*//* contract.start *//*contract.getStart()) || when.isEqual(contract.getStart()*//*
																																																		 * contract
																																																		 * .
																																																		 * start
																																																		 *//*))
			    && (when.isBefore(*//* contract.end *//*contract.getEnd()) || when.isEqual(*//*
																																									 * contract
																																									 * .
																																									 * end
																																									 *//*contract.getEnd()))) {
				return true;
			}
		}
		return false;
	}*/

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	@Override
	public String toString() {
		return String.format("Person(id=%s, matricule=%s, %s %s)", id, matricule, firstName, lastName);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isNonSynchronizable() {
		return nonSynchronizable;
	}

	public void setNonSynchronizable(boolean nonSynchronizable) {
		this.nonSynchronizable = nonSynchronizable;
	}

	@Override
	public int compareTo(Person o) {
		return 0;
	}

	public enum Gender {
		MALE, FEMALE
	}

	/*
	 * Ajouter par baha
	 */

	public enum Bureau {
		Siège, Annexe, Etage, Agence, BoxDeChange
	}

	public String getCIN() {
		return CIN;
	}

	public void setCIN(String cIN) {
		CIN = cIN;
	}

	public String getPersonnePrevenir() {
		return PersonnePrevenir;
	}

	public void setPersonnePrevenir(String personnePrevenir) {
		PersonnePrevenir = personnePrevenir;
	}

	public String getGroupeSanguin() {
		return GroupeSanguin;
	}

	public void setGroupeSanguin(String groupeSanguin) {
		GroupeSanguin = groupeSanguin;
	}

	public String getMedecinTraitant() {
		return MedecinTraitant;
	}

	public void setMedecinTraitant(String medecinTraitant) {
		MedecinTraitant = medecinTraitant;
	}

	public String getNumUrgence() {
		return NumUrgence;
	}

	public void setNumUrgence(String numUrgence) {
		NumUrgence = numUrgence;
	}

	public String getMaladie() {
		return Maladie;
	}

	public void setMaladie(String maladie) {
		Maladie = maladie;
	}

	public String getAffectation() {
		return Affectation;
	}

	public void setAffectation(String object) {
		Affectation = object;
	}

}
