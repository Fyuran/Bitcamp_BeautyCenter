package com.bitcamp.centro.estetico.models;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_credentials")
public class UserCredentials implements Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String username;

	private char[] password;

	private String address;

	private String iban;

	private String phone;

	private String mail;

	@OneToOne(mappedBy = "userCredentials", optional = false, cascade = CascadeType.MERGE)
	private User user;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public UserCredentials() {
		this.isEnabled = true;
	}

	public UserCredentials(String username, char[] password, String address, String iban, String phone,
			String mail, User user) {
		this(null, username, password, address, iban, phone, mail, user, true);
	}

	public UserCredentials(Long id, String username, char[] password, String address, String iban, String phone,
			String mail, User user, boolean isEnabled) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.address = address;
		this.iban = iban;
		this.phone = phone;
		this.mail = mail;
		this.user = user;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	private static void purgePasswordArray(char[] password) {
		for (int i = 0; i < password.length; i++) {
			password[i] = Character.MIN_VALUE;
		}
	}

	public static char[] encryptPassword(char[] password) {
		char[] encryptedPwd = BCrypt.withDefaults().hashToChar(5, password);
		purgePasswordArray(password);
		return encryptedPwd;
	}

	public boolean isValidPassword(char[] password) {
		return BCrypt.verifyer().verify(password, this.password).verified;
	}

	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", username=" + username + ", address="
				+ address + ", iban=" + iban + ", phone=" + phone + ", mail=" + mail + ", isEnabled=" + isEnabled + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((iban == null) ? 0 : iban.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((mail == null) ? 0 : mail.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		UserCredentials other = (UserCredentials) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (!Arrays.equals(password, other.password))
			return false;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (iban == null) {
			if (other.iban != null)
				return false;
		} else if (!iban.equals(other.iban))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (mail == null) {
			if (other.mail != null)
				return false;
		} else if (!mail.equals(other.mail))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		Map<String, Object> map = new LinkedHashMap<>();

			map.put("ID", id);
			map.put("Username", username);
			map.put("Indirizzo", address);
			map.put("IBAN", iban);
			map.put("Telefono", phone);
			map.put("Email", mail);
			map.put("Abilitato", isEnabled);

		return map;

	}
}
