package com.bitcamp.centro.estetico.models;
import java.time.LocalDate;

import it.kamaladafrica.codicefiscale.City;
import it.kamaladafrica.codicefiscale.CodiceFiscale;
import it.kamaladafrica.codicefiscale.Person;
import it.kamaladafrica.codicefiscale.city.CityByName;
import it.kamaladafrica.codicefiscale.city.CityProvider;


public class UserDetails {
	private String name;
	private String surname;
	private String birthplace;
	private Gender gender;
	private LocalDate BoD;
	private String notes;
	private CodiceFiscale EU_TIN;// codice fiscale

	// Costruttore
	public UserDetails(String name, String surname, Gender gender, LocalDate BoD, String birthplace, String notes) {
		this.name = name;
		this.surname = surname;
		this.birthplace = birthplace;
		this.gender = gender;
		this.BoD = BoD;
		this.notes = notes;
		this.EU_TIN = calculateEU_TIN(name, surname, birthplace, gender, BoD);
	}

	public UserDetails(String name, String surname, boolean gender, LocalDate BoD, String birthplace, String notes) {
		this(name, surname, Gender.fromBool(gender), BoD, birthplace, notes);
	}

	//Getter
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

	protected LocalDate getBoD() {
	    return BoD;
	}

	protected String getNotes() {
	    return notes;
	}

	protected CodiceFiscale getEU_TIN() {
		return EU_TIN;
	}

	//Setter
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

	protected void setBoD(LocalDate BoD) {
	    this.BoD = BoD;
	}

	protected void setNotes(String notes) {
	    this.notes = notes;
	}

	private static CodiceFiscale calculateEU_TIN(
			String name, String surname, String birthplace,
			Gender gender, LocalDate BoD
		) {
		CityByName cities = CityProvider.ofDefault();
		City city = null;
		try {
			city = cities.findByName(birthplace);
		} catch(IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		}

		Person person =	Person.builder()
				.firstname(name)
				.lastname(surname)
				.birthDate(BoD)
				.isFemale(gender.toBoolean())
				.city(city)
				.build();

		return CodiceFiscale.of(person);
	}

	@Override
	public String toString() {
		return "UserDetails [name=" + name + ", surname=" + surname + ", birthplace=" + birthplace + ", gender="
				+ gender + ", BoD=" + BoD + ", notes=" + notes + ", EU_TIN=" + EU_TIN + "]";
	}

}