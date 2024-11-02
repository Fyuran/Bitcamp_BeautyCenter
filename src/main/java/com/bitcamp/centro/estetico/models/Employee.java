package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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

	public Employee(Long id, UserDetails details, UserCredentials userCredentials,
			String employeeSerial, Roles role, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(id, details, userCredentials, true, employeeSerial, role, turns, hiredDate, terminationDate);
	}

	public Employee(UserDetails details, UserCredentials userCredentials,
			String employeeSerial, Roles role, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(null, details, userCredentials, employeeSerial, role, turns, hiredDate, terminationDate);
	}

	public Employee(UserDetails details, UserCredentials userCredentials,
			Roles role, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(null, details, userCredentials, UUID.randomUUID().toString(), role, turns, hiredDate, terminationDate);
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
	public Object[] toTableRow() {
		return new Object[] {
				getId(), getGender(), employeeSerial, getEu_tin(), getName(), getSurname(), getBoD(), hiredDate,
				terminationDate, role,
				getUsername(), getAddress(), getBirthplace(), getMail(), getPhone(), getIban(), isEnabled()
		};
	}
}
