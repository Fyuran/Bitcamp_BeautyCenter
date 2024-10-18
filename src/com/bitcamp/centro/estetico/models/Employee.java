package com.bitcamp.centro.estetico.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import com.bitcamp.centro.estetico.DAO.DAOShift;
import com.bitcamp.centro.estetico.DAO.EmployeeDAO;
import com.bitcamp.centro.estetico.DAO.TreatmentDAO;
import com.bitcamp.centro.estetico.DAO.UserCredentialsDAO;

public class Employee extends User {
    private long employeeSerial;
    private Roles role;
    private List<Shift> turns;
    private LocalDate hiredDate;
    private LocalDate terminationDate;
	private Treatment treatment;

	//User(int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled)
    //UserDetails(String name, String surname, boolean isFemale, LocalDate BoD, String birthplace, String notes)
	private Employee(
			int id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,

			long employeeSerial, Roles role, List<Shift> turns, LocalDate hiredDate, LocalDate terminationDate, Treatment treatment) {
		super(id, details, userCredentials, isEnabled);
		this.employeeSerial = employeeSerial;
		this.turns = turns;
		this.hiredDate = hiredDate;
		this.role = role;
		this.terminationDate = terminationDate;
		this.treatment = treatment;

	}
	public Employee(
			UserDetails details, UserCredentials userCredentials,
            long employeeSerial, Roles role, List<Shift> turns, LocalDate hiredDate, LocalDate terminationDate, Treatment treatment) {
		this(-1, details, userCredentials, true, employeeSerial, role, turns, hiredDate, terminationDate, treatment);
    }

	public Employee(ResultSet rs) throws SQLException {
		this(
				rs.getInt(1),
				new UserDetails(
					rs.getString(2), rs.getString(3),
					rs.getBoolean(4), rs.getDate(5).toLocalDate(),
					rs.getString(6), rs.getString(11)
				),
				UserCredentialsDAO.getUserCredentials(rs.getInt(10)).get(),
				rs.getBoolean(12),
				rs.getLong(13),
				Roles.toEnum(rs.getString(7)),
				DAOShift.loadShiftsForEmployeeId(rs.getInt(1)),//I metodi di DAOShift non dovrebbero essere statici?
				rs.getDate(8) != null ? rs.getDate(8).toLocalDate() : null,
				rs.getDate(9) != null ? rs.getDate(9).toLocalDate() : null,
				TreatmentDAO.getTreatment(rs.getInt(14)).orElse(null)
			);
	}

	public Employee(int id, Employee obj) {
		this(id, obj.getDetails(), obj.getUserCredentials(), obj.isEnabled(), obj.employeeSerial, obj.role, obj.turns,
				obj.hiredDate, obj.terminationDate, obj.getTreatment());
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

	public static long generateSerial() {
		Random rand = new Random();
		long serial = rand.nextInt(90000, Integer.MAX_VALUE);
		return EmployeeDAO.isSerialUnique(serial) ? serial : generateSerial();
	}

	public Treatment getTreatment() {
		return treatment;
	}
	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.getName(), this.getSurname());
	}
	
	
	//"ID", "Numero seriale", "Nome", "Cognome", "Data di nascita", "Assunzione", "Scadenza",
	//"Ruolo", "Username", "Indirizzo", "Città di provenienza", "Mail", "Telefono", "IBAN", "Trattamento", "Abilitato"
	@Override
	public Object[] toTableRow() {
		return new Object[] {
				getId(), employeeSerial, getName(), getSurname(), getBoD(), hiredDate, terminationDate, role,
				getUsername(), getAddress(), getBirthplace(), getMail(), getPhone(), getIban(), treatment, isEnabled()
		};
	}
}
