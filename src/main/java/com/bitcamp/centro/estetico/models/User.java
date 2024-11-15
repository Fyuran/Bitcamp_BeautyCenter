package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import org.hibernate.annotations.ColumnDefault;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@Inheritance
@DiscriminatorColumn(name = "user_type")
public abstract class User implements Model {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private UserDetails details;

	private Roles role;

	@OneToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "credentials_id", nullable = false)
	private UserCredentials userCredentials;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	User() {
		this.isEnabled = true;
		this.role = Roles.USER;
	}

	User(Roles role, UserDetails details, UserCredentials userCredentials) {
		this(null, role, details, userCredentials, true);
	}

	User(Long id, Roles role, UserDetails details, UserCredentials userCredentials, boolean isEnabled) {
		this.id = id;
		this.role = role;
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

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
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

	@Override
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

	public LocalDate getBirthday() {
		return details.getBirthday();
	}

	public String getNotes() {
		return details.getNotes();
	}

	public String getEu_tin() {
		return details.getEu_tin();
	}

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
		if (isFemale)
			details.setGender(Gender.FEMALE);
		else
			details.setGender(Gender.MALE);
	}

	public void setBoD(LocalDate BoD) {
		details.setBirthday(BoD);
	}

	public void setNotes(String notes) {
		details.setNotes(notes);
	}

	public void setEu_tin(String eu_tin) {
		details.setEu_tin(eu_tin);
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
		userCredentials.setPassword(password);
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
		return "User [id=" + id + ", details=" + details.toString() + ", userCredentials=" + userCredentials.toString()
				+ ", isEnabled="
				+ isEnabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((userCredentials == null) ? 0 : userCredentials.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (role != other.role)
			return false;
		if (userCredentials == null) {
			if (other.userCredentials != null)
				return false;
		} else if (!userCredentials.equals(other.userCredentials))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();

		JButton showCredentials = new JButton("Credenziali");
		showCredentials.addActionListener(l -> {
			ModelViewer<UserCredentials> picker = new ModelViewer<>("Credenziali",
					ListSelectionModel.SINGLE_SELECTION, getUserCredentials());
			picker.setVisible(true);
		});

		map.put("ID", id);
		map.put("Ruolo", role);
		map.putAll(details.toTableRow());
		map.put("Credenziali", showCredentials);
		map.put("Abilitato", isEnabled);

		return map;
	}
}
