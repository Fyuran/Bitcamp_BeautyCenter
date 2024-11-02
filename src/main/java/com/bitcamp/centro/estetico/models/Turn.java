package com.bitcamp.centro.estetico.models;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "turn")
public class Turn implements Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDateTime start;

	private LocalDateTime end;

	private TurnType type;

	private String notes;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "employees_turns", 
		joinColumns = @JoinColumn(name = "turn_id", referencedColumnName = "id", nullable = false), 
		inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
	)
	private List<Employee> assignedEmployees;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Turn() {
		this.type = TurnType.HOLIDAYS;
		this.isEnabled = true;
	}

	public Turn(Long id, LocalDateTime start, LocalDateTime end, TurnType type, String notes,
			List<Employee> assignedEmployees) {
		this(id, start, end, type, notes, assignedEmployees, true);
	}

	public Turn(LocalDateTime start, LocalDateTime end, TurnType type, String notes,
			List<Employee> assignedEmployees) {
		this(null, start, end, type, notes, assignedEmployees);
	}

	public Turn(Long id, LocalDateTime start, LocalDateTime end, TurnType type, String notes,
			List<Employee> assignedEmployees, boolean isEnabled) {
		this.id = id;
		this.start = start;
		this.end = end;
		this.type = type;
		this.notes = notes;
		this.assignedEmployees = assignedEmployees;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public TurnType getType() {
		return type;
	}

	public void setType(TurnType type) {
		this.type = type;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<Employee> getAssignedEmployees() {
		return assignedEmployees;
	}

	public void setAssignedEmployees(List<Employee> assignedEmployees) {
		this.assignedEmployees = assignedEmployees;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	@Override
	public String toString() {
		return "Shift [id=" + id + ", start=" + start + ", end=" + end + ", type=" + type + "]";
	}

	public Object[] toTableRow() {
		return new Object[] { id, start, end, type, notes, isEnabled };
	}
}
