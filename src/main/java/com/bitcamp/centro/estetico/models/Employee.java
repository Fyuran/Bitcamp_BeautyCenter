package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;

import com.bitcamp.centro.estetico.utils.ModelViewer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@DiscriminatorValue("E")
public class Employee extends User {

	@Column(name = "serial")
	private String employeeSerial;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(
		name = "employee_treatment",
		joinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "treatment_id", referencedColumnName = "id", nullable = false)
	)
	private List<Treatment> enabledTreatments;

	@ManyToMany(mappedBy = "assignedEmployees", fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Turn> turns;

	@Column(name = "hired")
	private LocalDate hiredDate;

	@Column(name = "termination")
	private LocalDate terminationDate;

	public Employee() {
		super();
		this.employeeSerial = UUID.randomUUID().toString();
		enabledTreatments = new ArrayList<>();
		turns = new ArrayList<>();
	}

	public Employee(UserDetails details, UserCredentials userCredentials,
			String employeeSerial, Roles role, List<Treatment> enabledTreatments, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(null, details, userCredentials, true, employeeSerial, role, enabledTreatments, turns, hiredDate, terminationDate);
	}

	public Employee(UserDetails details, UserCredentials userCredentials,
			Roles role, List<Treatment> enabledTreatments, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		this(null, details, userCredentials, true, UUID.randomUUID().toString(), role, enabledTreatments, turns, hiredDate, terminationDate);
	}

	public Employee(Long id, UserDetails details, UserCredentials userCredentials, boolean isEnabled,
			String employeeSerial, Roles role, List<Treatment> enabledTreatments, List<Turn> turns, LocalDate hiredDate, LocalDate terminationDate) {
		super(id, role, details, userCredentials, isEnabled);
		this.employeeSerial = employeeSerial;
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
		return super.getRole();
	}

	public void setRole(Roles role) {
		super.setRole(role);
	}

	public LocalDate getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(LocalDate terminationDate) {
		this.terminationDate = terminationDate;
	}

	public List<Treatment> getEnabledTreatments() {
		if(enabledTreatments == null) return new ArrayList<>();
		return enabledTreatments;
	}

	public void setEnabledTreatments(List<Treatment> enabledTreatments) {
		this.enabledTreatments = enabledTreatments;
	}

	public List<Turn> getTurns() {
		if(turns == null) return new ArrayList<>();
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
		int result = super.hashCode();
		result = prime * result + ((employeeSerial == null) ? 0 : employeeSerial.hashCode());
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
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (employeeSerial == null) {
			if (other.employeeSerial != null)
				return false;
		} else if (!employeeSerial.equals(other.employeeSerial))
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
		var superMap = super.toTableRow();
		JButton showTreatments = new JButton("Trattamenti");
		showTreatments.addActionListener(l -> {
			ModelViewer<Treatment> picker = new ModelViewer<>("Trattamenti",
					ListSelectionModel.SINGLE_SELECTION, getEnabledTreatments());
			picker.setVisible(true);
		});
		JButton showTurns = new JButton("Turni");
		showTreatments.addActionListener(l -> {
			ModelViewer<Turn> picker = new ModelViewer<>("Turni",
					ListSelectionModel.SINGLE_SELECTION, getTurns());
			picker.setVisible(true);
		});

		superMap.put("Matricola", employeeSerial);
		superMap.put("Assunto", hiredDate);
		superMap.put("Scadenza", terminationDate);
		superMap.put("Trattamenti", showTreatments);
		superMap.put("Turni", showTurns);
		
		return superMap;
	}
}
