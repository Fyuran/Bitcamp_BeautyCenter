package com.centro.estetico.bitcamp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import DAO.UserCredentialsDAO;

public class Employee extends User {
    private long employeeSerial;
    private Roles role;
    private List<Shift> turns;
    private LocalDate hiredDate;
    private LocalDate terminationDate;
    
    //User(int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled)
    //UserDetails(String name, String surname, boolean isFemale, LocalDate BoD, String birthplace, String notes)
	private Employee(
			int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,
			
			long employeeSerial, Roles role, List<Shift> turns, LocalDate hiredDate, LocalDate terminationDate) {
		super(id, details, userCredentials, isEnabled);
		this.employeeSerial = employeeSerial;
		this.turns = turns;
		this.hiredDate = hiredDate;
		this.role = role;
		this.terminationDate = terminationDate;
		
	}
	//costruttore senza password
	public Employee(
			UserDetails details, UserCredentials userCredentials,
            long employeeSerial, Roles role, List<Shift> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(-1, details, userCredentials, true, employeeSerial, role, turns, hiredDate, terminationDate);
    }
	
	public Employee(ResultSet rs) throws SQLException {
		this(
				rs.getInt(1),
				new UserDetails(
					rs.getString(2), rs.getString(3),
					rs.getBoolean(4), rs.getDate(5).toLocalDate(), 
					rs.getString(6), rs.getString(11)
				),
				UserCredentialsDAO.getUserCredentials(rs.getInt(9)).get(),
				rs.getBoolean(12),
				rs.getLong(13),
				Roles.valueOf(rs.getString(6)),
				new ArrayList<Shift>(), //TODO: Add DAO for Shift
				rs.getDate(8).toLocalDate(),
				rs.getDate(9).toLocalDate()
			);
	}
	
	public Employee(int id, Employee obj) {
		this(id, obj.getDetails(),  obj.getUserCredentials(), obj.isEnabled(), obj.employeeSerial, obj.role, obj.turns, obj.hiredDate, obj.terminationDate);
	}
	public long getEmployeeSerial() {
		return employeeSerial;
	}

	public void setEmployeeSerial(long employeeSerial) {
		this.employeeSerial = employeeSerial;
	}

	public List<Shift> getShift() {
		return turns;
	}

	public void setShift(List<Shift> turns) {
		this.turns = turns;
	}

	public LocalDate getHiredDate() {
		return hiredDate;
	}

	public void setHiredDate(LocalDate hiredDate) {
		this.hiredDate = hiredDate;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}
	// Metodo per aggiungere un turno
    public void addShift(Shift turns) {
        if (turns != null) {
            this.turns.add(turns); // Aggiungi il turno alla lista
        }
    }

    // Metodo per rimuovere un turno
    public void removeShift(Shift turns) {
        this.turns.remove(turns); // Rimuovi il turno dalla lista
    }

    // Getter per gli turns
    public List<Shift> getShifts() {
        return turns;
    }

	@Override
	public String toString() {
		return "Employee [employeeSerial=" + employeeSerial + ", role=" + role + ", turns=" + turns + ", hiredDate="
				+ hiredDate + ", terminationDate=" + terminationDate + ", toString()=" + super.toString() + "]";
	}
	public Object[] toTableRow() {
		return new Object[] {
				getId(), getName(), getSurname(), getBoD(), getBirthplace(), employeeSerial, role, turns, hiredDate, terminationDate, getNotes()
		};
	}
}
