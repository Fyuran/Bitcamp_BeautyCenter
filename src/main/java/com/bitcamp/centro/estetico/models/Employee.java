package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.bitcamp.centro.estetico.controller.DAO;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
@DiscriminatorValue("E")
public class Employee extends User {

	@Column(name = "serial")
	private String employeeSerial;

	private Roles role;

	@OneToMany
	@JoinTable(
		name = "employee_treatment",
		joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id", nullable = false)
	)
	private List<Treatment> enabledTreatments;

	@ManyToMany(mappedBy = "assignedEmployees", fetch = FetchType.EAGER)
	private List<Turn> turns;

	@Column(name = "hired")
	private LocalDate hiredDate;

	@Column(name = "termination")
	private LocalDate terminationDate;

	public Employee() {
		super();
		this.employeeSerial = UUID.randomUUID().toString();
		this.role = Roles.PERSONNEL;
	}

	public Employee(Map<String, Object> map, UserDetails details, UserCredentials userCredentials) {
		this(
			(Long) map.get("ID"),
			(UserDetails) map.get("Dettagli"), 
			(UserCredentials) map.get("Credenziali"), 
			(boolean) map.get("Abilitato"),
			(String) map.get("Matricola"), 
			(Roles) map.get("Codice Destinatario"), 
			(List<Turn>) map.get("Turni"),
			(LocalDate) map.get("Assunto"),
			(LocalDate) map.get("Scadenza")
		);
	}

	public Employee(UserDetails details, UserCredentials userCredentials,
			String employeeSerial, Roles role, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(null, details, userCredentials, true, employeeSerial, role, turns, hiredDate, terminationDate);
	}

	public Employee(UserDetails details, UserCredentials userCredentials,
			Roles role, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(null, details, userCredentials, true, UUID.randomUUID().toString(), role, turns, hiredDate, terminationDate);
	}

	public Employee(Long id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,
			String employeeSerial, Roles role, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		super(id, details, userCredentials, isEnabled);
		this.employeeSerial = employeeSerial;
		this.role = role;
		this.turns = turns;
		this.hiredDate = hiredDate;
		this.terminationDate = terminationDate;
	}

	public String getEmployeeSerial() {
		return employeeSerial;
	}

	public void setEmployeeSerial(String employeeSerial) {
		this.employeeSerial = employeeSerial;
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

	public List<Treatment> getEnabledTreatments() {
		return enabledTreatments;
	}

	public void setEnabledTreatments(List<Treatment> enabledTreatments) {
		this.enabledTreatments = enabledTreatments;
	}

	public List<Turn> getTurns() {
		return turns;
	}

	public void setTurns(List<Turn> turns) {
		this.turns = turns;
	}

	public void addTurn(Turn turns) {
		this.turns.add(turns);
	}

	public void removeTurn(Turn turns) {
		this.turns.remove(turns);
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.getName(), this.getSurname());
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((employeeSerial == null) ? 0 : employeeSerial.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((enabledTreatments == null) ? 0 : enabledTreatments.hashCode());
		result = prime * result + ((turns == null) ? 0 : turns.hashCode());
		result = prime * result + ((hiredDate == null) ? 0 : hiredDate.hashCode());
		result = prime * result + ((terminationDate == null) ? 0 : terminationDate.hashCode());
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
		Employee other = (Employee) obj;
		if (employeeSerial == null) {
			if (other.employeeSerial != null)
				return false;
		} else if (!employeeSerial.equals(other.employeeSerial))
			return false;
		if (role != other.role)
			return false;
		if (enabledTreatments == null) {
			if (other.enabledTreatments != null)
				return false;
		} else if (!enabledTreatments.equals(other.enabledTreatments))
			return false;
		if (turns == null) {
			if (other.turns != null)
				return false;
		} else if (!turns.equals(other.turns))
			return false;
		if (hiredDate == null) {
			if (other.hiredDate != null)
				return false;
		} else if (!hiredDate.equals(other.hiredDate))
			return false;
		if (terminationDate == null) {
			if (other.terminationDate != null)
				return false;
		} else if (!terminationDate.equals(other.terminationDate))
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		var map = super.toTableRow();
		map.putAll(Map.ofEntries(
			Map.entry("Matricola", employeeSerial),
			Map.entry("Assunto", hiredDate),
			Map.entry("Scadenza", terminationDate),
			Map.entry("Ruolo", role)
		));

		return map;
	}
}
