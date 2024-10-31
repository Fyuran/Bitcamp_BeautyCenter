package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;

import org.hibernate.annotations.ColumnDefault;

import it.kamaladafrica.codicefiscale.CodiceFiscale;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance
@DiscriminatorColumn(name="user_type")
public abstract class User implements Model {

	@Id
	@GeneratedValue
	private Long id;

	@Embedded
	private UserDetails details;

	@OneToOne(optional = false)
	@JoinColumn(name = "credentials_id")
	private UserCredentials userCredentials;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "false")
	private boolean isEnabled;

	User() {
		this.id = null;
		this.details = null;
		this.userCredentials = null;
		this.isEnabled = false;
	}

	User(Long id, UserDetails details, UserCredentials userCredentials, boolean isEnabled) {
		this.id = id;
		this.details = details;
		this.userCredentials = userCredentials;
		this.isEnabled = isEnabled;
	}

	@Override
	public Long getId() {
		return id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public UserDetails getDetails() {
		return details;
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public void setDetails(UserDetails details) {
		this.details = details;
	}

	public void setUserCredentials(UserCredentials userCredentials) {
		this.userCredentials = userCredentials;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public String getName() {
	    return details.getName();
	}

	public String getSurname() {
	    return details.getSurname();
	}

	public String getBirthplace() {
	    return details.getBirthplace();
	}

    public Gender getGender() {
        return details.getGender();
    }

	public LocalDate getBoD() {
	    return details.getBirthday();
	}

	public String getNotes() {
	    return details.getNotes();
	}

	public CodiceFiscale getEU_TIN() {
		return details.getEu_tin();
	}

	//Setter
	public void setName(String name) {
		details.setName(name);
	}

	public void setSurname(String surname) {
		details.setSurname(surname);
	}

	public void setBirthplace(String birthplace) {
	    details.setBirthplace(birthplace);
	}

	public void setGender(Gender gender) {
        details.setGender(gender);
    }
	public void setGender(boolean isFemale) {
        if(isFemale) details.setGender(Gender.FEMALE);
		else details.setGender(Gender.MALE);
    }

	public void setBoD(LocalDate BoD) {
	    details.setBirthday(BoD);
	}

	public void setNotes(String notes) {
	    details.setNotes(notes);
	}

	public Long getUserCredentialsId() {
		return userCredentials.getId();
	}

	public String getUsername() {
		return userCredentials.getUsername();
	}

	public void setUsername(String username) {
		userCredentials.setUsername(username);
	}
	
	public char[] getPassword() {
		return userCredentials.getPassword();
	}
	public void setPassword(char[] password) {
		userCredentials.setPassword(password);;
	}
	public boolean isValidPassword(char[] password) {
		return userCredentials.isValidPassword(password);
	}
	
	public String getAddress() {
		return userCredentials.getAddress();
	}

	public void setAddress(String address) {
		userCredentials.setAddress(address);
	}

	public String getIban() {
		return userCredentials.getIban();
	}

	public void setIban(String iban) {
		userCredentials.setIban(iban);
	}

	public String getPhone() {
		return userCredentials.getPhone();
	}

	public void setPhone(String phone) {
		userCredentials.setPhone(phone);
	}

	public String getMail() {
		return userCredentials.getMail();
	}

	public void setMail(String mail) {
		userCredentials.setMail(mail);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", details=" + details.toString() + ", userCredentials=" + userCredentials.toString() + ", isEnabled="
				+ isEnabled + "]";
	}

	public Object[] toTableRow() {
		return new Object[] {
				id, getName(), getSurname(), getBoD(), getBirthplace(), getNotes()
		};
	}
}
