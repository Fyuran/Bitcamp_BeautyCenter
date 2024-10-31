package com.bitcamp.centro.estetico.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;



public class UserCredentials implements Model{
	private Long id;
    private String username;
    private char[] password;//Criptata
    private String address;
    private String iban;
    private String phone;
    private String mail;
    private boolean isEnabled;

    private UserCredentials(Long id, String username, char[] password, String address, String iban, String phone, String mail, boolean isEnabled) {
    	this.id = id;
        this.username = username;
        this.password = encryptPassword(password);
        this.address = address;
        this.iban = iban;
        this.phone = phone;
        this.mail = mail;
        this.isEnabled = isEnabled;
    }

    public UserCredentials(String username, char[] password, String address, String iban, String phone, String mail) {
    	this(-1, username, password, address, iban, phone, mail, true);
    }

    public UserCredentials(String username, char[] password) {
    	this(-1, username, password, null, null, null, null, true);
    }

    public UserCredentials(Long id, UserCredentials obj) {
    	this(id, obj.username, obj.password, obj.address, obj.iban, obj.phone, obj.mail, obj.isEnabled);
    }

    //id, username, password, mail, iban, phone, is_enabled
    public UserCredentials(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1),
			rs.getString(2),
			(char[]) rs.getString(3).toCharArray(),
			rs.getString(4),
			rs.getString(5),
			rs.getString(6),
			rs.getString(7),
			rs.getBoolean(8)
		);
	}

	private static void purgePasswordArray(char[] password) {
		for(int i = 0; i < password.length; i++) {
			password[i] = Character.MIN_VALUE;
		}
	}
	// Metodi per gestire la criptazione e la decriptazione della password tramite la libreria Bcrypt
 	public static char[] encryptPassword(char[] password) {
		char[] encryptedPwd = BCrypt.withDefaults().hashToChar(5, password);
		purgePasswordArray(password);
 		return encryptedPwd;
 	}
 	public boolean isValidPassword(char[] password) {
 	    return BCrypt.verifyer().verify(password, this.password).verified;
 	}
	
	 @Override
	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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


	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = encryptPassword(password);
	}

	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", username=" + username  + ", address="
				+ address + ", iban=" + iban + ", phone=" + phone + ", mail=" + mail + ", isEnabled=" + isEnabled + "]";
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public Object[] toTableRow() {
		return new Object[] {
				id, username, password, address, iban, phone, mail, isEnabled
		};
	}
}
