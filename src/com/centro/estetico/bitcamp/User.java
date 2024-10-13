package com.centro.estetico.bitcamp;

import java.time.LocalDate;

import it.kamaladafrica.codicefiscale.CodiceFiscale;

public abstract class  User {
	private final int id;
	private UserDetails details;
	private UserCredentials userCredentials;
	private boolean isEnabled;

	protected User(int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled) {
		this.id = id;
		this.details = details;
		this.userCredentials = userCredentials;
		this.isEnabled = isEnabled;
	}

	public int getId() {
		return id;
	}

	public UserDetails getDetails() {
		return details;
	}

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}

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

    public boolean isFemale() {
        return details.isFemale();
    }

	public LocalDate getBoD() {
	    return details.getBoD();
	}

	public String getNotes() {
	    return details.getNotes();
	}

	public CodiceFiscale getEU_TIN() {
		return details.getEU_TIN();
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

	public void setIsFemale(boolean isFemale) {
        details.setIsFemale(isFemale);
    }

	public void setBoD(LocalDate BoD) {
	    details.setBoD(BoD);
	}

	public void setNotes(String notes) {
	    details.setNotes(notes);
	}

	public int getUserCredentialsId() {
		return userCredentials.getId();
	}

	public String getUsername() {
		return userCredentials.getUsername();
	}

	public void setUsername(String username) {
		userCredentials.setUsername(username);
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
