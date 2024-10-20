package com.bitcamp.centro.estetico.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class UserCredentials {
	private final int id;
    private String username;//Non criptata
    private String password;//Criptata
    private String address;
    private String iban;//Modifica effettuata - L'iban non può essere criptato in quanto l'amministratore
    //ha bisogno di vedere quale sia l'iban degli operatori e siccome non si tratta di una criptazione
    //simile al cifrario di cesare, si può solo verificare se un dato inserito corrisponde oppure no
    private String phone;
    private String mail;
    private boolean isEnabled;

    private UserCredentials(int id, String username, String password, String address, String iban, String phone, String mail) {
    	this.id = id;
        this.username = username;
        this.password = encryptPassword(password);
        this.address = address;
        this.iban = iban;
        this.phone = phone;
        this.mail = mail;
    }
    
    public UserCredentials(String username, String password, String address, String iban, String phone, String mail) {
    	this(-1, username, password, address, iban, phone, mail);
    }
    
    public UserCredentials(int id, UserCredentials obj) {
    	this(id, obj.username, obj.password, obj.address, obj.iban, obj.phone, obj.mail);
    }
    
    //id, username, password, mail, iban, phone, is_enabled
    public UserCredentials(ResultSet rs) throws SQLException {
		this(
			rs.getInt(1),
			rs.getString(2),
			rs.getString(3),
			rs.getString(4),
			rs.getString(5),
			rs.getString(6),
			rs.getString(7)
		);
	}

	// Metodi per gestire la criptazione e la decriptazione della password tramite la libreria Bcrypt
 	private String encryptPassword(String password) {
 		//Utilizzeremo le impostazioni di default fornite dalla libreria
 		//Creiamo un array di caratteri per la password dopodiché verrà eseguito un numero di 5 iterazioni per criptare la password
 		char[] cryptStringChars = password.toCharArray();
 		String bcryptPassword = BCrypt.withDefaults().hashToString(5, cryptStringChars);
 		return bcryptPassword;
 	}
 	public void changePassword(String oldPassword, String newPassword) {
		if (isValidPassword(oldPassword)) {
			this.password = encryptPassword(newPassword);
		}
	}
 	public boolean isValidPassword(String password) {
 		//Facciamo una verifica così da permettere la modifica delle credenziali da parte dell'utente
 	    char[] cryptStringChars = password.toCharArray();
 	    BCrypt.Result verificationResult = BCrypt.verifyer().verify(cryptStringChars, this.password);
 	    return verificationResult.verified;
 	}

	public int getId() {
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

	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = encryptPassword(password);
	}

	@Override
	public String toString() {
		return "UserCredentials [id=" + id + ", username=" + username + ", password=" + password + ", address="
				+ address + ", iban=" + iban + ", phone=" + phone + ", mail=" + mail + ", isEnabled=" + isEnabled + "]";
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}

	public Object[] toTableRow() {
		return new Object[] {
				id, username, password, address, iban, phone, mail, isEnabled
		};
	}
}
