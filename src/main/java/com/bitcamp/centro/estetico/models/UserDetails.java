package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

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

	public UserDetails() {
	}

	public UserDetails(String name, String surname, Gender gender, LocalDate birthday, String birthplace,
			String notes) {
		this(name, surname, gender, birthday, birthplace, notes,
				calculateEU_TIN(name, surname, birthplace, gender, birthday));
	}

	public UserDetails(String name, String surname, Gender gender, LocalDate birthday, String birthplace, String notes,
			String eu_tin) {
		this.name = name;
		this.surname = surname;
		this.birthplace = birthplace;
		this.gender = gender;
		this.birthday = birthday;
		this.notes = notes;

		if (eu_tin == null || eu_tin.isBlank()) {
			this.eu_tin = calculateEU_TIN(name, surname, birthplace, gender, birthday);
		} else {
			this.eu_tin = eu_tin;
		}
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
		if (eu_tin == null || eu_tin.isBlank())
			eu_tin = calculateEU_TIN(name, surname, birthplace, gender, birthday);
		this.eu_tin = eu_tin;
	}

	protected String getEu_tin() {
		if (eu_tin == null || eu_tin.isBlank())
			eu_tin = calculateEU_TIN(name, surname, birthplace, gender, birthday);
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

	protected void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	protected void setNotes(String notes) {
		this.notes = notes;
	}

	private static String calculateEU_TIN(
			String name, String surname, String birthplace,
			Gender gender, LocalDate birthday) {
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
				.birthDate(birthday)
				.isFemale(gender.toBoolean())
				.city(city)
				.build();

		return CodiceFiscale.of(person).getValue();
	}

	@Override
	public String toString() {
		return "UserDetails [name=" + name + ", surname=" + surname + ", birthplace=" + birthplace + ", gender="
				+ gender + ", birthday=" + birthday + ", notes=" + notes + ", EU_TIN=" + eu_tin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		result = prime * result + ((birthplace == null) ? 0 : birthplace.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((eu_tin == null) ? 0 : eu_tin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDetails other = (UserDetails) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		if (birthplace == null) {
			if (other.birthplace != null)
				return false;
		} else if (!birthplace.equals(other.birthplace))
			return false;
		if (gender != other.gender)
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (eu_tin == null) {
			if (other.eu_tin != null)
				return false;
		} else if (!eu_tin.equals(other.eu_tin))
			return false;
		return true;
	}

	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();

		map.put("Nome", name);
		map.put("Cognome", surname);
		map.put("Sesso", gender);
		map.put("Data di nascita", birthday);
		map.put("Luogo di nascita", birthplace);
		map.put("C.F", eu_tin);
		map.put("Note", notes);

		return map;
	}
}