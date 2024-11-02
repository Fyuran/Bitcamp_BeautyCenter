package com.bitcamp.centro.estetico.models;

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

	@OneToOne(mappedBy = "userCredentials", cascade = CascadeType.PERSIST)
	private User user;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public UserCredentials() {
		this.isEnabled = true;
	}

	public UserCredentials(Long id, String username, char[] password, String address, String iban, String phone,
			String mail, User user) {
		this(id, username, password, address, iban, phone, mail, user, true);
	}

	public UserCredentials(String username, char[] password, String address, String iban, String phone,
			String mail, User user) {
		this(null, username, password, address, iban, phone, mail, user);
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

	public Object[] toTableRow() {
		return new Object[] {
				id, username, password, address, iban, phone, mail, isEnabled
		};
	}

}
