package com.bitcamp.centro.estetico.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reservation")
public class Reservation implements Model {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@OneToOne
	@JoinColumn(name = "treatment_id", nullable = false)
	private Treatment treatment;

	@OneToMany
	@JoinTable(
		name = "reservation_employee",
		joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
	)
	private List<Employee> employees;

	private LocalDate date;

	private LocalTime time;

	private ReservationState state;

	@Column(name = "is_enabled")
	@ColumnDefault(value = "true")
	private boolean isEnabled;

	public Reservation() {
		this.isEnabled = true;
	}

	public Reservation(Map<String, Object> map) {
		this(			
			(Long) map.get("ID"),
			(Customer) map.get("Cliente"),
			(Treatment) map.get("Trattamento"),
			(List<Employee>) map.get("Clienti"),
			(LocalDate) map.get("Data"),
			(LocalTime) map.get("Orario"),
			(ReservationState) map.get("Stato"),
			(boolean) map.get("Abilitato")
		);
	}

	public Reservation(Customer customer, Treatment treatment, List<Employee> employees, LocalDate date,
			LocalTime time, ReservationState state) {
		this(null, customer, treatment, employees, date, time, state, true);
	}

	public Reservation(Long id, Customer customer, Treatment treatment, List<Employee> employees, LocalDate date,
			LocalTime time, ReservationState state, boolean isEnabled) {
		this.id = id;
		this.customer = customer;
		this.treatment = treatment;
		this.employees = employees;
		this.date = date;
		this.time = time;
		this.state = state;
		this.isEnabled = isEnabled;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public ReservationState getState() {
		return state;
	}

	public void setState(ReservationState state) {
		this.state = state;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	@Override
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((treatment == null) ? 0 : treatment.hashCode());
		result = prime * result + ((employees == null) ? 0 : employees.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Reservation other = (Reservation) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (treatment == null) {
			if (other.treatment != null)
				return false;
		} else if (!treatment.equals(other.treatment))
			return false;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		if (state != other.state)
			return false;
		if (isEnabled != other.isEnabled)
			return false;
		return true;
	}

	@Override
	public Map<String, Object> toTableRow() {
		return Map.ofEntries(
			Map.entry("ID", id),
			Map.entry("Cliente", customer),
			Map.entry("Trattamento", treatment),
			Map.entry("Data", date),
			Map.entry("Orario", time),
			Map.entry("Stato", state),
			Map.entry("Abilitato", isEnabled)
		);
	}

}
