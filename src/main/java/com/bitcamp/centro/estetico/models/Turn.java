package com.bitcamp.centro.estetico.models;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

	public Turn(Map<String, Object> map) {
		this(
			(Long) map.get("ID"),
			(LocalDateTime) map.get("Inizio"),
			(LocalDateTime) map.get("Fine"),
			(TurnType) map.get("Tipo"),
			(String) map.get("Note"),
			(List<Employee>) map.get("Assegnati"),
			(boolean) map.get("Abilitato")
		);
	}

	public Turn(LocalDateTime start, LocalDateTime end, TurnType type, String notes,
			List<Employee> assignedEmployees) {
		this(null, start, end, type, notes, assignedEmployees, true);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + ((end == null) ? 0 : end.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((notes == null) ? 0 : notes.hashCode());
		result = prime * result + ((assignedEmployees == null) ? 0 : assignedEmployees.hashCode());
		result = prime * result + (isEnabled ? 1231 : 1237);
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
		Turn other = (Turn) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (end == null) {
			if (other.end != null)
				return false;
		} else if (!end.equals(other.end))
			return false;
		if (type != other.type)
			return false;
		if (notes == null) {
			if (other.notes != null)
				return false;
		} else if (!notes.equals(other.notes))
			return false;
		if (assignedEmployees == null) {
			if (other.assignedEmployees != null)
				return false;
		} else if (!assignedEmployees.equals(other.assignedEmployees))
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		return Map.ofEntries(
			Map.entry("ID", id),
			Map.entry("Inizio", start),
			Map.entry("Fine", end),
			Map.entry("Tipo", type),
			Map.entry("Note", notes),
			Map.entry("Abilitato", isEnabled)
		);
	}

}
