package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;

import it.kamaladafrica.codicefiscale.City;
import it.kamaladafrica.codicefiscale.CodiceFiscale;
import it.kamaladafrica.codicefiscale.Person;
import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;
import jakarta.persistence.Embeddable;

@Embeddable
public class UserDetails {
	private String name;
	private String surname;
	private String birthplace;
	private Gender gender;
	private LocalDate birthday;
	private String notes;
	private String eu_tin;

	public UserDetails() {}

	public UserDetails(String name, String surname, Gender gender, LocalDate BoD, String birthplace, String notes) {
		this(name, surname, gender, BoD, birthplace, notes, calculateEU_TIN(name, surname, birthplace, gender, BoD));
	}

	public UserDetails(String name, String surname, Gender gender, LocalDate BoD, String birthplace, String notes, String eu_tin) {
		this.name = name;
		this.surname = surname;
		this.birthplace = birthplace;
		this.gender = gender;
		this.birthday = BoD;
		this.notes = notes;
		this.eu_tin = eu_tin;
	}

	protected String getName() {
		return name;
	}

	protected String getSurname() {
		return surname;
	}

	protected String getBirthplace() {
		return birthplace;
	}

	protected Gender getGender() {
		return gender;
	}

	protected LocalDate getBirthday() {
		return birthday;
	}

	protected String getNotes() {
		return notes;
	}

	protected void setEu_tin(String eu_tin) {
		this.eu_tin = eu_tin;
	}

	protected String getEu_tin() {
		if(eu_tin == null) eu_tin = calculateEU_TIN(name, surname, birthplace, gender, birthday);
		return eu_tin;
	}

	// Setter
	protected void setName(String name) {
		this.name = name;
	}

	protected void setSurname(String surname) {
		this.surname = surname;
	}

	protected void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	protected void setGender(Gender gender) {
		this.gender = gender;
	}

	protected void setBirthday(LocalDate BoD) {
		this.birthday = BoD;
	}

	protected void setNotes(String notes) {
		this.notes = notes;
	}

	private static String calculateEU_TIN(
			String name, String surname, String birthplace,
			Gender gender, LocalDate BoD) {
		CityByName cities = CityProvider.ofDefault();
		City city = null;
		try {
			city = cities.findByName(birthplace);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}

		Person person = Person.builder()
				.firstname(name)
				.lastname(surname)
				.birthDate(BoD)
				.isFemale(gender.toBoolean())
				.city(city)
				.build();

		return CodiceFiscale.of(person).getValue();
	}

	@Override
	public String toString() {
		return "UserDetails [name=" + name + ", surname=" + surname + ", birthplace=" + birthplace + ", gender="
				+ gender + ", BoD=" + birthday + ", notes=" + notes + ", EU_TIN=" + eu_tin + "]";
	}

}