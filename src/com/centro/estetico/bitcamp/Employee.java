package com.centro.estetico.bitcamp;

import java.time.*;
import java.util.ArrayList;
import java.util.EnumSet;
import at.favre.lib.crypto.bcrypt.BCrypt;

public class Employee extends User {
	private long employeeSerial;// Potremmo anche metterci solo un normalissimo int che arriva a 2^63 ma ci
								// atteniamo al diagramma UML 
	private ArrayList<Shift> turns;
	private LocalDate hiredDate;
	private EnumSet<Roles> role;// Lo metto qua anche perché non ha senso crearsi una classe a parte, forse era
								// solo per rendere leggibile l'UML

	public enum Roles {
		PERSONNEL("Operatore"), SECRETARY("Segretario"), ADMIN("Admin");
		Roles(String roleName) {
			this.roleName=roleName;
		}

		private String roleName;
		public String getRoleName() {
			return roleName;
		}
	}

	private LocalDate terminationDate;
	private String iban;
	private String address;
	private String phone;
	private String mail;
	private boolean isEnabled;
	// Campi per le credenziali che dobbiamo criptare - o solo la password utilizza
	// bcrypt di Daniel
	private String username;
	private String password;

	//Da rivedere la questione dell'ID con Mauro
	
	public Employee(int id, String name, String surname, String birthplace, boolean isFemale, LocalDate BoD,
			String notes,boolean isEnabled, long employeeSerial, ArrayList<Shift> turns, LocalDate hiredDate, EnumSet<Roles> role,
			LocalDate terminationDate, String iban, String address, String phone, String mail, String username, String password) {
		super(id, name, surname, birthplace, isFemale, BoD, notes, isEnabled);
		this.employeeSerial = employeeSerial;
		this.turns = turns;
		this.hiredDate = hiredDate;
		this.role = role;
		this.terminationDate = terminationDate;
		this.iban = hashIban(iban); // Criptiamo l'IBAN
		this.address = address;
		this.phone = phone;
		this.mail = mail;
		this.isEnabled = isEnabled;
		this.username = username;
		this.password = hashPassword(password); // Criptiamo la password
	}

	
	//Metodi per gestire la criptazione e la decriptazione della password utilizzando la libreria Bcrypt Daniel
	 private String hashPassword(String password) {
	  
			String passwordCrypt = BCrypt.withDefaults().hashToString(12, password.toCharArray());
			return passwordCrypt;
	    }

	    // Metodo per criptare l'IBAN
	    private String hashIban(String iban) {
	    	String ibanCrypt = BCrypt.withDefaults().hashToString(12, iban.toCharArray());
			return ibanCrypt;
	    }
	// Getter
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;//Tanto è criptata
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getMail() {
		return mail;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public long getEmployeeSerial() {
		return employeeSerial;
	}

	public ArrayList<Shift> getTurns() {
		return turns;
	}

	public LocalDate getHiredDate() {
		return hiredDate;
	}

	public EnumSet<Roles> getRole() {
		return role;
	}
	public String getRolesAsString() {
	    String rolesAsString = "";
	    for (Roles r : role) {
	        rolesAsString+=r.getRoleName()+", ";
	    }
	    return rolesAsString.length() > 0 ? rolesAsString.substring(0, rolesAsString.length() - 2) : "";
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public String getIban() {
		return iban;
	}

	// Setter
	public void setUsername(String username) {
		this.username = username;
	}



	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setEnabled(boolean enabled) {
		isEnabled = enabled;
	}

	public void setEmployeeSerial(long employeeSerial) {
		this.employeeSerial = employeeSerial;
	}

	public void setTurns(ArrayList<Shift> turns) {
		this.turns = turns;
	}

	public void setHiredDate(LocalDate hiredDate) {
		this.hiredDate = hiredDate;
	}

	public void setRole(EnumSet<Roles> role) {
		this.role = role;
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	

	public void setPassword(String password) {
	    this.password = hashPassword(password);//E ricodifichiamo la password
	}
	 public void setIban(String iban) {
	        this.iban = hashIban(iban);//Stessa cosa
	    }
	
	 public boolean isValidPassword(String password) {
	        return BCrypt.verifyer().verify(password.toCharArray(), this.password).verified;
	       //https://www.example-code.com/java/bcrypt_verify_password.asp
	        
	 }

	    public void changePassword(String oldPassword, String newPassword) {
	        if (isValidPassword(oldPassword) && isValidPassword(newPassword)) {
	            this.password = hashPassword(newPassword);
	        }
	    }
	 
	 
	// Metodo per verificare la validità della password
	/*public boolean isValidPassword(String password) {
		// Check se è null o vuota
		if (password == null || password.isEmpty()) {
			return false;
		}

		// Bastano 8 caratteri...
		if (password.length() < 8) {
			return false;
		}
		
		
	
		// Check ogni carattere per vedere se contiente almeno un numero ed una lettera - Non C'è il Character.isSpesicalCharacter quindi ho dovuto fare il check con alcuni caratteri predefinit - Dirlo a Daniel siccome è meglio usare la stessa metodologia anche per Beauty Center
		boolean CheckCharactersUpperCase = false;
		boolean CheckNumbers = false;
		boolean CheckCharactersLowerCase = false;
		boolean CheckSomeSpecialCharacter = false;
		for (char character : password.toCharArray()) {

			// Controlliamo caratteri maiuscoli
			if (Character.isUpperCase(character)) {
				CheckCharactersUpperCase = true;
			}

			// Controlliamo carrateri minuscoli
			if (Character.isLowerCase(character)) {
				CheckCharactersLowerCase = true;
			}

			// Controlliamo caratteri numerici
			if (Character.isDigit(character)) {
				CheckNumbers = true;
			}
			
			//Controlliamo solo ALCUNI caratteri speciali
			if(character =='!'||character =='@'||character =='?'||character =='_')
			{
				CheckSomeSpecialCharacter = true;
			}
		}
		if (CheckCharactersUpperCase == true && CheckCharactersLowerCase == true && CheckNumbers == true&& CheckSomeSpecialCharacter==true) 
		{
			return true;
		}
		else 
		{
			return false;
		}

	}

*/
	
	    /*
	    // Metodo per cambiare la password
	public void changePassword(String oldPassword, String newPassword) {
		if (this.password.equals(oldPassword) && isValidPassword(newPassword)) {
			this.password = newPassword;
		}
	}
*/
	// Metodo per verificare se l'impiegato è ancora attivo
	public boolean isActive() {
		return terminationDate == null || terminationDate.isAfter(LocalDate.now());
	}

	// Metodo per aggiungere un turno
	public void addShift(Shift shift) {
		turns.add(shift);
	}

	// Metodo per rimuovere un turno
	public void removeShift(Shift shift) {
		turns.remove(shift);
	}

	@Override
	public String toString() {
		//Il superToString ricava il ToString dalla classe astratta
		return super.toString() + "Employee [employeeSerial=" + employeeSerial + ", turns=" + turns + ", hiredDate=" + hiredDate
				+ ", role=" + role + ", terminationDate=" + terminationDate +" address=" + address
				+ ", phone=" + phone + ", mail=" + mail + ", isEnabled=" + isEnabled + ", username=" + username
				+ ", toString()=" + "]";
	}

	
}
