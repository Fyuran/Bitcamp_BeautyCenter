package com.centro.estetico.bitcamp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

import utils.inputValidator;

public class Employee extends User {
    private long employeeSerial;
    private ArrayList<Shift> shift;
    private LocalDate hiredDate;
  
    private LocalDate terminationDate;
    private UserCredentials userCredentials;
    public enum Roles {
        PERSONNEL("Operatore"), SECRETARY("Receptionist"), ADMIN("Admin");
    	String role;
    	Roles(String role){
    		this.role=role;
    	}
    	public  String getRole() {
    		return role;
    	}
    	public static Roles fromString(String role) {
            for (Roles r : Roles.values()) {
                if (r.getRole().equalsIgnoreCase(role)) {
                    return r;
                }
            }
            throw new IllegalArgumentException("Nessun ruolo trovato per la descrizione: " + role);
        }
    }
    public Roles roles;
   

	public Employee(
		int id, String name, String surname, String birthplace, boolean isFemale, LocalDate BoD,String notes, boolean isEnabled, 
		long employeeSerial, ArrayList<Shift> shift, LocalDate hiredDate,Roles roles, LocalDate terminationDate,
		String username, String password, String address, String iban, String phone, String mail) {
		super(id, name, surname, birthplace, isFemale, BoD, notes, isEnabled);
		this.employeeSerial = employeeSerial;
		this.shift = new ArrayList<>();
		this.hiredDate = hiredDate;
		this.roles = roles;
		this.terminationDate = terminationDate;
		this.userCredentials = new UserCredentials(username, password, address,iban, phone, mail);
	}
	//costruttore senza id
	public Employee(
			String name, String surname, String birthplace, boolean isFemale, LocalDate BoD,String notes, boolean isEnabled, 
			long employeeSerial, ArrayList<Shift> shift, LocalDate hiredDate,Roles roles, LocalDate terminationDate,
			String username, String password, String address, String iban, String phone, String mail) {
			super(name, surname, birthplace, isFemale, BoD, notes, isEnabled);
			this.employeeSerial = employeeSerial;
			this.shift = new ArrayList<>();
			this.hiredDate = hiredDate;
			this.roles = roles;
			this.terminationDate = terminationDate;
			this.userCredentials = new UserCredentials(username, password, address,iban, phone, mail);
		}
	
	
	
	
	//Costruttore vuoto
	public Employee() {
	    super(-1, "", "", "", false, LocalDate.now(), "", true); // valori predefiniti
	    this.employeeSerial = 0; // valore predefinito
	    this.shift = new ArrayList<>(); // inizializza come lista vuota
	    this.hiredDate = LocalDate.now(); // data attuale come valore predefinito
	    this.roles = Roles.PERSONNEL; // valore predefinito per i ruoli
	    this.terminationDate = null; // o una data predefinita
	    this.userCredentials = new UserCredentials("", "", "", "", "", ""); // inizializza con valori vuoti
	}

	//Rivedere il costruttore vuoto

	public Roles getRole() {
        return roles;
    }

    public void setRole(Roles roles) {
        this.roles = roles;
    }
	public long getEmployeeSerial() {
		return employeeSerial;
	}

	public void setEmployeeSerial(long employeeSerial) {
		this.employeeSerial = employeeSerial;
	}



	public ArrayList<Shift> getShift() {
		return shift;
	}



	public void setShift(ArrayList<Shift> shift) {
		this.shift = shift;
	}



	public LocalDate getHiredDate() {
		return hiredDate;
	}



	public void setHiredDate(LocalDate hiredDate) {
		this.hiredDate = hiredDate;
	}



	public Roles getRoles() {
		return roles;
	}



	public void setRoles(Roles roles) {
		this.roles = roles;
	}



	public LocalDate getTerminationDate() {
		return terminationDate;
	}



	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}


	// Metodo per aggiungere un turno
    public void addShift(Shift shift) {
        if (shift != null) {
            this.shift.add(shift); // Aggiungi il turno alla lista
        }
    }

    // Metodo per rimuovere un turno
    public void removeShift(Shift shift) {
        this.shift.remove(shift); // Rimuovi il turno dalla lista
    }

    // Getter per gli shift
    public ArrayList<Shift> getShifts() {
        return shift;
    }

	public UserCredentials getUserCredentials() {
		return userCredentials;
	}
	public String getBoDString() {
		DateTimeFormatter format=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		return super.getBoD().format(format);
	}
	
	private static long generateSerial() {
		Random rand=new Random();
		long serial=rand.nextLong(900000)+100000;//generazione casuale di un numero a 6 cifre
		//check che il serial sia univoco. Se non lo è, ripete la funzione
		return inputValidator.isSerialUnique(serial)?serial:generateSerial();
	}
	
	

	@Override
	public String toString() {
		return "Employee [employeeSerial=" + employeeSerial + ", shift=" + shift + ", hiredDate=" + hiredDate
				+ ", roles=" + roles + ", terminationDate=" + terminationDate + ", userCredentials=" + userCredentials
				+ ", getId()=" + getId() + ", getName()=" + getName() + ", getSurname()=" + getSurname()
				+ ", getBirthplace()=" + getBirthplace() + ", getIsFemale()=" + getIsFemale() + ", getBoD()=" + getBoD()
				+ ", getNotes()=" + getNotes() + ", GetIsEnabled()=" + GetIsEnabled() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}






}
