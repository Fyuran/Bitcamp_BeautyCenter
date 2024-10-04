package com.centro.estetico.bitcamp;

import java.time.LocalDate;
import java.util.ArrayList;

public class Employee extends User {
    private long employeeSerial;
    private ArrayList<Shift> shift;
    private LocalDate hiredDate;
    private Roles roles;
    private LocalDate terminationDate;
    private UserCredentials userCredentials;
    private enum Roles {
        PERSONNEL, SECRETARY, ADMIN
    }

   

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
	public Employee(
            String name, String surname, String birthplace, boolean isFemale, LocalDate BoD, String notes, boolean isEnabled, 
            long employeeSerial, ArrayList<Shift> shift, LocalDate hiredDate, Roles roles, LocalDate terminationDate,
            String username, String password, String address, String iban, String phone, String mail) {
        this(-1, name, surname, birthplace, isFemale, BoD, notes, isEnabled, employeeSerial, shift, hiredDate, roles, terminationDate, username, password, address, iban, phone, mail);
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
